package univ.earthbreaker.namu.database.core.friend;

import org.springframework.stereotype.Repository;

import univ.earthbreaker.namu.core.domain.friend.FriendRepository;

@Repository
public class FriendRepositoryAdapter implements FriendRepository {

	private final FriendJpaRepository friendJpaRepository;

	public FriendRepositoryAdapter(FriendJpaRepository friendJpaRepository) {
		this.friendJpaRepository = friendJpaRepository;
	}

	@Override
	public void register(long memberNo, long targetMemberNo) {
		friendJpaRepository.save(FriendJpaEntity.create(memberNo, targetMemberNo));
	}
}
