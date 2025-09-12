package univ.earthbreaker.namu.infra.notification;

import org.springframework.stereotype.Component;

@Component
public interface NotificationPort {

	void sendShowOffMessage(PushNotificationSourceCommand sourceCommand);

	void sendAfterFollow(FollowPushNotificationSourceCommand sourceCommand);

	void sendAfterGift(GiftPushNotificationSourceCommand sourceCommand);
}
