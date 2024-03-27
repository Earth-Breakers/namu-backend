package univ.earthbreaker.namu.external.notification;

import static org.mockito.Mockito.anyList;
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
class NotificationAdapterTest {

	private @Mock FirebaseMessaging firebaseMessaging;
	private @Mock FcmNotificationCallbackHandlerRegister fcmNotificationCallbackHandlerRegister;
	private @InjectMocks NotificationAdapter notificationAdapter;

	@DisplayName("""
		푸시 알림 정보
		(보내는 회원의 별명, 자랑할 캐릭터 이름, 자랑 메시지, 메시지를 전송받을 회원들의 푸시 알림 토큰값)
		를 인자로 받아 '자랑하기' 메시지를 전송한다""")
	@Test
	void sendShowOffMessage() {
		// given
		ApiFuture<BatchResponse> messageFuture = mock(ApiFuture.class);
		when(firebaseMessaging.sendEachAsync(anyList()))
			.thenReturn(messageFuture);

		// when
		notificationAdapter.sendShowOffMessage(NOTIFICATION_SOURCE_COMMAND);

		// then
		verify(firebaseMessaging).sendEachAsync(anyList());
		verify(fcmNotificationCallbackHandlerRegister).register(messageFuture, NOTIFICATION_SOURCE_COMMAND);
	}
}
