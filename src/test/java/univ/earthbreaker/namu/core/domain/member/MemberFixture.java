package univ.earthbreaker.namu.core.domain.member;

import static univ.earthbreaker.namu.core.domain.member.MemberStatus.ACTIVE;

import java.util.Collections;
import java.util.List;

import univ.earthbreaker.namu.core.domain.member.friend.Following;
import univ.earthbreaker.namu.core.domain.member.friend.Friend;

public class MemberFixture {

	public static final long MEMBER_NO = 1L;
	public static final int MEMBER_LEVEL = 1;
	public static final String MEMBER_NICKNAME = "member_nickname";
	public static final Member MEMBER = new Member(MEMBER_NO, MEMBER_NICKNAME, MEMBER_LEVEL, ACTIVE);

	public static final int REQUIRED_EXP = 1000;
	public static final int CURRENT_EXP = 100;
	public static final int ACCUMULATE_EXP = 100;
	public static final Member _MEMBER
		= new Member(MEMBER_NO, MEMBER_NICKNAME, MEMBER_LEVEL, REQUIRED_EXP, CURRENT_EXP, ACCUMULATE_EXP, ACTIVE);

	public static final long FOLLOWING_MEMBER_NO = 2L;
	public static final int FOLLOWING_MEMBER_LEVEL = 1;
	public static final String FOLLOWING_MEMBER_NICKNAME = "friend_nickname";
	public static final List<Following> FOLLOWINGS = List.of(
		new Following(FOLLOWING_MEMBER_NO, FOLLOWING_MEMBER_LEVEL, FOLLOWING_MEMBER_NICKNAME));
	public static final List<Following> NO_FOLLOWINGS = Collections.emptyList();
	public static final Friend FRIEND_EXIST = new Friend(MEMBER_NO, FOLLOWINGS);
	public static final Friend FRIEND_NOT_EXIST = new Friend(MEMBER_NO, NO_FOLLOWINGS);
}
