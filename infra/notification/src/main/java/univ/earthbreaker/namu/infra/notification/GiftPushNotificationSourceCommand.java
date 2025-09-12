package univ.earthbreaker.namu.services.notification;

public record GiftPushNotificationSourceCommand(
	String nickname,
	String notificationToken
) {
}
