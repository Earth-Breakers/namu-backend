package univ.earthbreaker.namu.external.notification;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static univ.earthbreaker.namu.external.notification.FCMNotificationFixture.FAILURE_FCM_TOKENS;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotificationReSendHandlerTest {

	private @Mock FirebaseBackOff firebaseBackOff;
	private @Mock NotificationReSender notificationReSender;
	private @InjectMocks NotificationReSendHandler notificationReSendHandler;

	@DisplayName("""
		전송 실패한 메시지를 재전송한다 -
		쓰레드 2개를 생성해 동시성을 테스트한다
		실패한 알림 토큰값이 최대 재전송 시도 횟수(3회) 동안 모두 실패하고
		2개의 쓰레드 각각 3번씩 재전송을 시도한다""")
	@Test
	void resendFailureMessage() throws InterruptedException {
		// given
		PushNotificationSourceCommand sourceCommand = mock(PushNotificationSourceCommand.class);
		when(sourceCommand.notificationTokens())
			.thenReturn(FAILURE_FCM_TOKENS);
		when(firebaseBackOff.isStopped())
			.thenReturn(
				false, false, false, true,
				false, false, false, true
			);
		when(notificationReSender.resendFailure(sourceCommand))
			.thenAnswer(invocation -> {
				BatchMessageResponse messageResponse = mock(BatchMessageResponse.class);
				when(messageResponse.hasFailure()).thenReturn(true);
				when(messageResponse.extractFailureTokens(FAILURE_FCM_TOKENS)).thenReturn(FAILURE_FCM_TOKENS);
				return messageResponse;
			});

		int thread = 2;
		ExecutorService executorService = Executors.newFixedThreadPool(thread);
		CountDownLatch latch = new CountDownLatch(thread);

		// when
		for (int i = 0; i < thread; i++) {
			executorService.submit(() -> {
				try {
					notificationReSendHandler.handleFailureMessage(sourceCommand);
				} finally {
					latch.countDown();
				}
			});
		}

		// then
		latch.await();
		executorService.shutdown();
		assertAll(
			() -> verify(firebaseBackOff, times(6)).nextBackOffMillis(),
			() -> verify(firebaseBackOff, times(6)).waitUntilInterval(),
			() -> verify(notificationReSender, times(6)).resendFailure(any()),
			() -> verify(firebaseBackOff, times(2)).reset()
		);
	}
}
