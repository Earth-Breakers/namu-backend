package univ.earthbreaker.namu.database.core.character;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import univ.earthbreaker.namu.core.domain.character.CharacterType;

public interface CharacterJpaRepository extends JpaRepository<CharacterJpaEntity, Long> {

	CharacterProjection findByType(CharacterType type);

	@NotNull CharacterJpaEntity findByNo(long characterNo);

	@Nullable CharacterJpaEntity findByLevelAndGroupNumberAndType(int level, int groupNumber, CharacterType type);

	@Query(
		value = """
			SELECT c.* FROM character c
			WHERE c.level = :level
			    AND c.group_number = :groupNumber
			    AND c.is_endangered = :isEndangered
			    AND c.type = :type
			ORDER BY RAND() LIMIT 1""",
		nativeQuery = true)
	@Nullable CharacterJpaEntity findRandomBy(int level, int groupNumber, boolean isEndangered, CharacterType type);
}
