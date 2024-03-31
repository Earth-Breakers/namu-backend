package univ.earthbreaker.namu.database.core.character;

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
		    cc.exp = :currentExp, cc.requiredExp = :requiredExp, cc.mainImagePath = :mainImagePath
		WHERE cc.memberNo = :memberNo""")
	void updateCurrentCharacter(
		long characterNo,
		CharacterType characterType,
		int level,
		int requiredExp,
		int currentExp,
		String mainImagePath,
		long memberNo
	);

	@Nullable CurrentCharacterJpaEntity findByMemberNo(long memberNo);
}
