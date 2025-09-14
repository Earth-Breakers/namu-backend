package univ.earthbreaker.namu.infra.notification;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;

import univ.earthbreaker.namu.core.domain.pushnotification.infra.ShowOffNotificationPort;

@Component
public class ShowOffNotificationAdapter implements ShowOffNotificationPort {

	private final FcmNotificationCallbackHandlerRegister fcmNotificationCallbackHandlerRegister;
	private final FirebaseMessaging firebaseMessaging;

	public ShowOffNotificationAdapter(
		FcmNotificationCallbackHandlerRegister fcmNotificationCallbackHandlerRegister,
		FirebaseMessaging firebaseMessaging
	) {
		this.fcmNotificationCallbackHandlerRegister = fcmNotificationCallbackHandlerRegister;
		this.firebaseMessaging = firebaseMessaging;
	}

	@Override
	public void sendShowOffMessage(PushNotificationSourceCommand sourceCommand) {
		List<Message> messages = MessageCreator.createMessages(sourceCommand);
		ApiFuture<BatchResponse> notificationFuture = firebaseMessaging.sendEachAsync(messages);
		fcmNotificationCallbackHandlerRegister.register(notificationFuture, sourceCommand);
	}
}
