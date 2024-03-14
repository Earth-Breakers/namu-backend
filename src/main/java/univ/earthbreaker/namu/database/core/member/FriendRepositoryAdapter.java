package univ.earthbreaker.namu.database.core.member;

import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import univ.earthbreaker.namu.core.domain.member.friend.Following;
import univ.earthbreaker.namu.core.domain.member.friend.Friend;
import univ.earthbreaker.namu.core.domain.member.friend.FriendRepository;

@Repository
public class FriendRepositoryAdapter implements FriendRepository {

	private final FriendJpaRepository friendJpaRepository;
	private final MemberJpaRepository memberJpaRepository;

	public FriendRepositoryAdapter(FriendJpaRepository friendJpaRepository, MemberJpaRepository memberJpaRepository) {
		this.friendJpaRepository = friendJpaRepository;
		this.memberJpaRepository = memberJpaRepository;
	}

	@Override
	public void register(long memberNo, long targetMemberNo) {
		friendJpaRepository.save(FriendJpaEntity.create(memberNo, targetMemberNo));
	}

	@Override
	public @NotNull Friend find(long memberNo) {
		List<MemberJpaEntity> followingMembers = memberJpaRepository.findFriendBy(memberNo);
		if (followingMembers.isEmpty()) {
			return new Friend(memberNo, Collections.emptyList());
		}
		List<Following> followings = followingMembers
			.stream()
			.map(MemberJpaEntity::toFollowing)
			.toList();
		return new Friend(memberNo, followings);
	}
}
