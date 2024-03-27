package univ.earthbreaker.namu.core.domain.member.friend;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.pushnotification.FriendsQuery;
import univ.earthbreaker.namu.core.domain.pushnotification.FriendBridge;

@Component
public class FriendBridgeAdapter implements FriendBridge {

	private final FriendFinder friendFinder;

	public FriendBridgeAdapter(FriendFinder friendFinder) {
		this.friendFinder = friendFinder;
	}

	@Override
	public @NotNull FriendsQuery findFriends(long memberNo) {
		Friend friend = friendFinder.find(memberNo);
		return new FriendsQuery(friend.getFollowingMemberNos());
	}
}
