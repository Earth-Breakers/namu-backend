package univ.earthbreaker.namu.database.core.character;

import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CurrentCharacterJpaRepository extends JpaRepository<CurrentCharacterJpaEntity, Long> {

	@Modifying
	@Query("""
		UPDATE CurrentCharacterJpaEntity cc
		SET cc.characterNo = :characterNo, cc.level = :level, cc.exp = 0,
		    cc.requiredExp = :requiredExp, cc.mainImagePath = :mainImagePath
		WHERE cc.memberNo = :memberNo""")
	void updateCurrentCharacter(long characterNo, int level, int requiredExp, String mainImagePath, long memberNo);

	@Nullable CurrentCharacterJpaEntity findByMemberNo(long memberNo);
}
