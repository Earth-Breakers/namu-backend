package univ.earthbreaker.namu.external.notification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.SendResponse;

@ExtendWith(MockitoExtension.class)
class BatchMessageResponseTest {

	private @Mock BatchResponse batchResponse;
	private @InjectMocks BatchMessageResponse batchMessageResponse;

	@DisplayName("fcm 을 통해 보낸 메시지의 비동기 결과(ApiFuture<BatchResponse>)를 인자로 받아 BatchMessageResponse 를 생성한다")
	@Test
	void success_from() throws ExecutionException, InterruptedException {
		// given
		ApiFuture<BatchResponse> apiFuture = mock(ApiFuture.class);
		when(apiFuture.get())
			.thenReturn(batchResponse);

		// when
		BatchMessageResponse actual = BatchMessageResponse.from(apiFuture);

		// then
		assertThat(actual).isEqualTo(batchMessageResponse);
	}

	@DisplayName("""
		fcm 을 통해 보낸 메시지의 비동기 결과(ApiFuture<BatchResponse>) 를 가져오는 중
		비동기 연산 예외가 발생하면
		NotificationException 예외를 발생시킨다""")
	@Test
	void fail_execution_exception_from() throws ExecutionException, InterruptedException {
		// given
		ApiFuture<BatchResponse> apiFuture = mock(ApiFuture.class);
		ExecutionException executionException = mock(ExecutionException.class);
		Throwable throwable = mock(Throwable.class);
		when(executionException.getCause())
			.thenReturn(throwable);
		when(throwable.getMessage())
			.thenReturn("ExecutionException 발생");
		when(apiFuture.get())
			.thenThrow(executionException);

		// when, then
		assertThatThrownBy(() -> BatchMessageResponse.from(apiFuture))
			.isInstanceOf(NotificationException.class)
			.hasMessage(NotificationException.sendAsync("ExecutionException 발생").getMessage());
	}

	@DisplayName("""
		fcm 을 통해 보낸 메시지의 비동기 결과(ApiFuture<BatchResponse>) 를 가져오는 중
		스레드 인터럽트가 발생하면
		NotificationException 예외를 발생시킨다""")
	@Test
	void fail_interrupted_exception_from() throws ExecutionException, InterruptedException {
		// given
		ApiFuture<BatchResponse> apiFuture = mock(ApiFuture.class);
		InterruptedException interruptedException = mock(InterruptedException.class);
		when(interruptedException.getMessage())
			.thenReturn("InterruptedException 발생");
		when(apiFuture.get())
			.thenThrow(interruptedException);

		// when, then
		assertThatThrownBy(() -> BatchMessageResponse.from(apiFuture))
			.isInstanceOf(NotificationException.class)
			.hasMessage(NotificationException.sendAsync("InterruptedException 발생").getMessage());
	}

	@DisplayName("전송에 실패한 메시지가 0개이면 false 를 반환하고, 1개 이상이면 true 를 반환한다")
	@ParameterizedTest
	@CsvSource({"0, false", "1, true"})
	void hasFailure(int count, boolean expect) {
		// given
		when(batchResponse.getFailureCount())
			.thenReturn(count);

		// when
		boolean actual = batchMessageResponse.hasFailure();

		// then
		assertThat(actual).isEqualTo(expect);
	}

	@DisplayName("메시지 전송 결과와 푸시 알림 토큰 값을 비교해, 전송에 실패한 토큰값의 앞 3글자와 전송 실패 이유를 Map 형태로 반환한다")
	@Test
	void getFailureReasons() {
		// given
		SendResponse sendResponse1 = mock(SendResponse.class);
		when(sendResponse1.isSuccessful()).thenReturn(true);

		SendResponse sendResponse2 = mock(SendResponse.class);
		when(sendResponse2.isSuccessful()).thenReturn(true);

		SendResponse sendResponse3 = mock(SendResponse.class);
		FirebaseMessagingException firebaseMessagingException = mock(FirebaseMessagingException.class);
		when(sendResponse3.isSuccessful()).thenReturn(false);
		when(sendResponse3.getException()).thenReturn(firebaseMessagingException);
		when(sendResponse3.getException().getMessage()).thenReturn("FCM Exception");

		when(batchResponse.getResponses())
			.thenReturn(List.of(sendResponse1, sendResponse2, sendResponse3));

		// when
		Map<String, String> actual = batchMessageResponse.getFailureTokensAndReasons(FCMNotificationFixture.FCM_TOKENS);

		// then
		assertAll(
			() -> assertThat(actual).isNotNull(),
			() -> assertThat(actual).hasSize(1),
			() -> assertThat(actual).containsEntry("789", "FCM Exception")
		);
	}

	@DisplayName("메시지 전송 결과와 푸시 알림 토큰 값을 비교해, 전송에 실패한 토큰값을 찾아 반환한다")
	@Test
	void extractFailureTokens() {
		// given
		SendResponse sendResponse1 = mock(SendResponse.class);
		when(sendResponse1.isSuccessful()).thenReturn(true);

		SendResponse sendResponse2 = mock(SendResponse.class);
		when(sendResponse2.isSuccessful()).thenReturn(true);

		SendResponse sendResponse3 = mock(SendResponse.class);
		when(sendResponse3.isSuccessful()).thenReturn(false);

		when(batchResponse.getResponses())
			.thenReturn(List.of(sendResponse1, sendResponse2, sendResponse3));

		// when
		List<String> actual = batchMessageResponse.extractFailureTokens(FCMNotificationFixture.FCM_TOKENS);

		// then
		assertAll(
			() -> assertThat(actual).isNotNull(),
			() -> assertThat(actual).hasSize(1),
			() -> assertThat(actual).contains(FCMNotificationFixture.FAILURE_FCM_TOKEN)
		);
	}

	@DisplayName("메시지 전송에 실패한 결과의 개수를 반환한다")
	@Test
	void getFailureCount() {
		// given
		int givenFailureCount = 1;
		when(batchResponse.getFailureCount())
			.thenReturn(givenFailureCount);

		// when
		int actual = batchMessageResponse.getFailureCount();

		// then
		assertThat(actual).isEqualTo(givenFailureCount);
	}

	@DisplayName("메시지 전송에 성공한 결과의 개수를 반환한다")
	@Test
	void getSuccessCount() {
		// given
		int givenSuccessCount = 2;
		when(batchResponse.getSuccessCount())
			.thenReturn(givenSuccessCount);

		// when
		int actual = batchMessageResponse.getSuccessCount();

		// then
		assertThat(actual).isEqualTo(givenSuccessCount);
	}
}
