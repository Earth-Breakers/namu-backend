package univ.earthbreaker.namu.db.core.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendJpaRepository extends JpaRepository<FriendJpaEntity, Long> {

	boolean existsByMasterMemberNoAndTargetMemberNo(long materMemberNo, long targetMemberNo);
}
