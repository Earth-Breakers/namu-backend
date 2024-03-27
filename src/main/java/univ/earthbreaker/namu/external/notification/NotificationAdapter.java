package univ.earthbreaker.namu.external.notification;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;

@Component
public class NotificationAdapter implements NotificationPort {

	private final FirebaseMessaging firebaseMessaging;
	private final FcmNotificationCallbackHandlerRegister fcmNotificationCallbackHandlerRegister;

	public NotificationAdapter(
		FirebaseMessaging firebaseMessaging,
		FcmNotificationCallbackHandlerRegister fcmNotificationCallbackHandlerRegister
	) {
		this.firebaseMessaging = firebaseMessaging;
		this.fcmNotificationCallbackHandlerRegister = fcmNotificationCallbackHandlerRegister;
	}

	@Override
	public void sendShowOffMessage(@NotNull PushNotificationSourceCommand sourceCommand) {
		List<Message> messages = MessageCreator.createMessages(sourceCommand);
		ApiFuture<BatchResponse> notificationFuture = firebaseMessaging.sendEachAsync(messages);
		fcmNotificationCallbackHandlerRegister.register(notificationFuture, sourceCommand);
	}
}
