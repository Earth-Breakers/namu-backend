package univ.earthbreaker.namu.core.domain.member.friend;

import java.util.Collections;
import java.util.List;

public class FriendFixture {

	public static final long MEMBER_NO = 1L;
	public static final long FOLLOWING_MEMBER_NO = 2L;
	public static final int FOLLOWING_MEMBER_LEVEL = 1;
	public static final String FOLLOWING_MEMBER_NICKNAME = "nickname";
	public static final List<Following> FOLLOWINGS = List.of(
		new Following(FOLLOWING_MEMBER_NO, FOLLOWING_MEMBER_LEVEL, FOLLOWING_MEMBER_NICKNAME));
	public static final List<Following> NO_FOLLOWINGS = Collections.emptyList();
	public static final Friend FRIEND_EXIST = new Friend(MEMBER_NO, FOLLOWINGS);
	public static final Friend FRIEND_NOT_EXIST = new Friend(MEMBER_NO, NO_FOLLOWINGS);
}
