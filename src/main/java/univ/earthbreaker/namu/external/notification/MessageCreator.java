package univ.earthbreaker.namu.external.notification;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.Message;

public class MessageCreator {

	private static final String NOTIFICATION_TITLE_FORMAT = "%s 님이 %s 를 뽑았어요";

	private MessageCreator() {
	}

	static List<Message> createMessages(@NotNull PushNotificationSourceCommand sourceCommand) {
		String title = String.format(NOTIFICATION_TITLE_FORMAT, sourceCommand.nickname(), sourceCommand.characterName());
		String messageBody = sourceCommand.messageBody();
		return sourceCommand.notificationTokens()
			.stream()
			.map(fcmToken -> create(fcmToken, title, messageBody))
			.toList();
	}

	private static Message create(String fcmTokenValue, String title, String messageBody) {
		return Message.builder()
			.setToken(fcmTokenValue)
			.setAndroidConfig(createAndroidConfig(title, messageBody))
			.build();
	}

	private static AndroidConfig createAndroidConfig(String title, String messageBody) {
		AndroidNotification androidNotification = AndroidNotification.builder()
			.setTitle(title)
			.setBody(messageBody)
			.setDefaultSound(true)
			.build();
		return AndroidConfig.builder()
			.setNotification(androidNotification)
			.build();
	}
}
