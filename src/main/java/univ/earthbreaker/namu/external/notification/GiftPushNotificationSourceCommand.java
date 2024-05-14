package univ.earthbreaker.namu.external.notification;

public record GiftPushNotificationSourceCommand(
	String nickname,
	String notificationToken
) {
}
