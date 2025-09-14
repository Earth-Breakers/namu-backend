package univ.earthbreaker.namu.core.domain.point.infra;

public interface EnergyPointNotificationPort {

	void sendAfterGift(GiftPushNotificationSourceCommand sourceCommand);

	record GiftPushNotificationSourceCommand(
		String nickname,
		String notificationToken
	) {
	}
}
