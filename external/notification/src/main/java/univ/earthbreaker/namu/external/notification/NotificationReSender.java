package univ.earthbreaker.namu.external.notification;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;

@Component
public class NotificationReSender {

	private final FirebaseMessaging firebaseMessaging;

	public NotificationReSender(FirebaseMessaging firebaseMessaging) {
		this.firebaseMessaging = firebaseMessaging;
	}

	BatchMessageResponse resendFailure(PushNotificationSourceCommand failureSourceCommand) {
		List<Message> messages = MessageCreator.createMessages(failureSourceCommand);
		return BatchMessageResponse.from(firebaseMessaging.sendEachAsync(messages));
	}
}
