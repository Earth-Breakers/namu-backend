package univ.earthbreaker.namu.core.service.member.friend;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.member.friend.Friend;
import univ.earthbreaker.namu.core.service.pushnotification.FriendBridge;
import univ.earthbreaker.namu.core.domain.pushnotification.infra.FriendsQuery;

@Component
public class FriendBridgeAdapter implements FriendBridge {

	private final FriendFinder friendFinder;

	public FriendBridgeAdapter(FriendFinder friendFinder) {
		this.friendFinder = friendFinder;
	}

	@Override
	public FriendsQuery findFriends(long memberNo) {
		Friend friends = friendFinder.findAll(memberNo);
		return new FriendsQuery(friends.getFollowingMemberNos());
	}
}
