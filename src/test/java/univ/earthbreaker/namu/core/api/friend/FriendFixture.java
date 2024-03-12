package univ.earthbreaker.namu.core.api.friend;

import java.util.Collections;
import java.util.List;

import univ.earthbreaker.namu.core.domain.member.friend.Following;
import univ.earthbreaker.namu.core.domain.member.friend.Friend;

public class FriendFixture {

	static final long MEMBER_NO = 1L;
	static final long FOLLOWING_MEMBER_NO = 2L;
	static final int FOLLOWING_MEMBER_LEVEL = 1;
	static final String FOLLOWING_MEMBER_NICKNAME = "nickname";
	static final List<Following> FOLLOWINGS = List.of(
		new Following(FOLLOWING_MEMBER_NO, FOLLOWING_MEMBER_LEVEL, FOLLOWING_MEMBER_NICKNAME));
	static final List<Following> NO_FOLLOWINGS = Collections.emptyList();
	static final Friend FRIEND_EXIST = new Friend(MEMBER_NO, FOLLOWINGS);
	static final Friend FRIEND_NOT_EXIST = new Friend(MEMBER_NO, NO_FOLLOWINGS);
}
