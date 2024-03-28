package univ.earthbreaker.namu.database.core.character;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberCharacterJpaRepository extends JpaRepository<MemberCharacterJpaEntity, Long> {

	@Nullable MemberCharacterJpaEntity findByMemberNoAndCharacterNo(long memberNo, long characterNo);

	@Query("""
		SELECT
		    mc.no, mc.memberNo, mc.characterNo, mc.count,
		    c.type, c.thumbnailImagePath,
		    COUNT(c) OVER (PARTITION BY c.type) AS totalCountPerType,
		    CASE WHEN mc.no IS NOT NULL THEN TRUE ELSE FALSE END AS isAcquired
		FROM MemberCharacterJpaEntity mc
		    RIGHT JOIN CharacterJpaEntity c
		        ON mc.characterNo = c.no AND mc.memberNo = :memberNo
		ORDER BY mc.characterNo""")
	List<MemberCharacterBookProjection> findMemberCharacterBookByMemberNo(long memberNo);
}
