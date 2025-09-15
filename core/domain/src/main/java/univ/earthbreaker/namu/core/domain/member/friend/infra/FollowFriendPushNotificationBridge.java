package univ.earthbreaker.namu.core.domain.member.friend.infra;

public interface FollowFriendPushNotificationBridge {

	FollowResult find(long memberNo, long targetMemberNo);

	record FollowResult(
		String memberNickname,
		String targetNickname,
		String targetTokenValue
	) {
	}
}
