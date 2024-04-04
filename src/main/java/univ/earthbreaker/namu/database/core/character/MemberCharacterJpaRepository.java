package univ.earthbreaker.namu.database.core.character;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberCharacterJpaRepository extends JpaRepository<MemberCharacterJpaEntity, Long> {

	@Nullable MemberCharacterJpaEntity findByMemberNoAndCharacterNo(long memberNo, long characterNo);

	@Query("""
		SELECT
		    mc.no AS no, mc.memberNo AS memberNo, mc.characterNo AS characterNo, mc.count AS count,
		    c.type AS type, c.thumbnailImagePath AS thumbnailImagePath,
		    COUNT(c) OVER (PARTITION BY c.type) AS totalCountPerType,
		    CASE WHEN mc.no IS NOT NULL THEN TRUE ELSE FALSE END AS isAcquired
		FROM MemberCharacterJpaEntity mc
		    RIGHT JOIN CharacterJpaEntity c
		        ON mc.characterNo = c.no AND mc.memberNo = :memberNo
		WHERE c.level = 4
		ORDER BY mc.characterNo""")
	List<MemberCharacterBookProjection> findMemberCharacterBookByMemberNo(long memberNo);
}
