package univ.earthbreaker.namu.infra.notification;

public record FollowPushNotificationSourceCommand(
	String nickname,
	String targetNickname,
	String notificationToken
) {
}
