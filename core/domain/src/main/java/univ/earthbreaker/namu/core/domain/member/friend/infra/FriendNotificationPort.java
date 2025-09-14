package univ.earthbreaker.namu.core.domain.member.friend.infra;

public interface FriendNotificationPort {

	void sendAfterFollow(FollowPushNotificationSourceCommand sourceCommand);

	record FollowPushNotificationSourceCommand(
		String nickname,
		String targetNickname,
		String notificationToken
	) {
	}
}
