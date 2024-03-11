package univ.earthbreaker.namu.database.core.character;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberCharacterBookJpaRepository extends JpaRepository<MemberCharacterBookJpaEntity, Long> {

	@Query("""
		SELECT
		    mcb.no, mcb.memberNo, mcb.characterNo, mcb.count,
		    c.type, c.thumbnailImagePath,
		    COUNT(c) OVER (PARTITION BY c.type) AS totalCountPerType,
		    CASE WHEN mcb.no IS NOT NULL THEN TRUE ELSE FALSE END AS isAcquired
		FROM MemberCharacterBookJpaEntity mcb
		    RIGHT JOIN CharacterJpaEntity c
		        ON mcb.characterNo = c.no AND mcb.memberNo = :memberNo
		ORDER BY mcb.characterNo""")
	List<MemberCharacterBookProjection> findMemberCharacterBookByMemberNo(long memberNo);
}
