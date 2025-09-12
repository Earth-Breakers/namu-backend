package univ.earthbreaker.namu.infra.notification;

public record GiftPushNotificationSourceCommand(
	String nickname,
	String notificationToken
) {
}
