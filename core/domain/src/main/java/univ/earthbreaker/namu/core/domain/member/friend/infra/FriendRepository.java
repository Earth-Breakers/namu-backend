package univ.earthbreaker.namu.core.domain.member.friend.infra;

import univ.earthbreaker.namu.core.domain.member.friend.Friend;

public interface FriendRepository {

	 void register(long memberNo, long targetMemberNo);

	 Friend findAll(long memberNo);

	 boolean existsBy(long memberNo, long targetMemberNo);
}
