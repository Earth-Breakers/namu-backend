package univ.earthbreaker.namu.services.notification;

public record FollowPushNotificationSourceCommand(
	String nickname,
	String targetNickname,
	String notificationToken
) {
}
