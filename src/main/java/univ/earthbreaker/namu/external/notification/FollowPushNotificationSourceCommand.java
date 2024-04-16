package univ.earthbreaker.namu.external.notification;

public record FollowPushNotificationSourceCommand(
	String nickname,
	String targetNickname,
	String notificationToken
) {
}
