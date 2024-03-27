package univ.earthbreaker.namu.external.notification;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static univ.earthbreaker.namu.external.notification.FCMNotificationFixture.NOTIFICATION_SOURCE_COMMAND;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;

@ExtendWith(MockitoExtension.class)
class NotificationReSenderTest {

	private @Mock FirebaseMessaging firebaseMessaging;
	private @InjectMocks NotificationReSender notificationReSender;

	@DisplayName("메시지 전송에 실패한 토큰 정보를 인자로 받아 재전송하고, 재전송 결과 객체를 반환한다")
	@Test
	void resendFailureMessage() {
		// given
		ApiFuture<BatchResponse> messageFuture = mock(ApiFuture.class);
		when(firebaseMessaging.sendEachAsync(anyList()))
			.thenReturn(messageFuture);

		// when
		notificationReSender.resendFailure(NOTIFICATION_SOURCE_COMMAND);

		// then
		verify(firebaseMessaging).sendEachAsync(anyList());
	}
}
