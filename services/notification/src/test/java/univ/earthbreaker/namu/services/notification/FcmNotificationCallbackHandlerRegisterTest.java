package univ.earthbreaker.namu.services.notification;

import static org.mockito.Mockito.*;
import static univ.earthbreaker.namu.services.notification.FCMNotificationFixture.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.api.core.ApiFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.firebase.messaging.BatchResponse;

@ExtendWith(MockitoExtension.class)
class FcmNotificationCallbackHandlerRegisterTest {

	private @Mock NotificationReSendHandler notificationReSendHandler;
	private @InjectMocks FcmNotificationCallbackHandlerRegister fcmNotificationCallbackHandlerRegister;

	@DisplayName("비동기 메시지 전송 결과 객체를 받아 콜백 핸들러를 등록한다")
	@Test
	void register() {
		// given
		ApiFuture<BatchResponse> messageFuture = mock(ApiFuture.class);

		// when
		fcmNotificationCallbackHandlerRegister.register(messageFuture, NOTIFICATION_SOURCE_COMMAND);

		// then
		verify(messageFuture, times(1))
			.addListener(any(Runnable.class), eq(MoreExecutors.directExecutor()));
	}
}
