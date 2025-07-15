package univ.earthbreaker.namu.db.core.character;

import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import univ.earthbreaker.namu.core.domain.character.CharacterType;

public interface CurrentCharacterJpaRepository extends JpaRepository<CurrentCharacterJpaEntity, Long> {

	@Modifying
	@Query("""
		UPDATE CurrentCharacterJpaEntity cc
		SET cc.characterNo = :characterNo, cc.characterType = :characterType, cc.level = :level,
		    cc.currentExp = :currentExp, cc.requiredExp = :requiredExp, cc.mainImagePath = :mainImagePath,
		    cc.backgroundImagePath = :backgroundImagePath, cc.scripts = :scripts
		WHERE cc.memberNo = :memberNo""")
	void updateCurrentCharacter(
		long characterNo,
		CharacterType characterType,
		int level,
		int requiredExp,
		int currentExp,
		String mainImagePath,
		String backgroundImagePath,
		String scripts,
		long memberNo
	);

	@Nullable CurrentCharacterJpaEntity findByMemberNo(long memberNo);
}
