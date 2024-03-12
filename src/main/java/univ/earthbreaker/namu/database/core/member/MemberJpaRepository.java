package univ.earthbreaker.namu.database.core.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {

	@Query("""
		SELECT m FROM MemberJpaEntity m
		WHERE m.no IN (
		    SELECT f.targetMemberNo
		    FROM FriendJpaEntity f
		    WHERE f.masterMemberNo = :memberNo)""")
	List<MemberJpaEntity> findFriendBy(long memberNo);
}
