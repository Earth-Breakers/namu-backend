package univ.earthbreaker.namu.infra.notification;

import org.springframework.stereotype.Component;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;

import univ.earthbreaker.namu.core.domain.point.infra.EnergyPointNotificationPort;

@Component
public class EnergyPointNotificationAdapter implements EnergyPointNotificationPort {

	private final FirebaseMessaging firebaseMessaging;

	public EnergyPointNotificationAdapter(FirebaseMessaging firebaseMessaging) {
		this.firebaseMessaging = firebaseMessaging;
	}

	@Override
	public void sendAfterGift(GiftPushNotificationSourceCommand sourceCommand) {
		String title = String.format("%s 님이 에너지를 보냈어요", sourceCommand.nickname());
		Message message = MessageCreator.create(sourceCommand.notificationToken(), title, null);
		firebaseMessaging.sendAsync(message);
	}
}
