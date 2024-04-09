package univ.earthbreaker.namu.database.core.character;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

import univ.earthbreaker.namu.core.domain.character.CharacterRepository;
import univ.earthbreaker.namu.core.domain.character.NamuCharacter;
import univ.earthbreaker.namu.core.domain.character.NextDeterminedDbQuery;
import univ.earthbreaker.namu.core.domain.character.NextRandomCharacterDbQuery;

@Repository
public class CharacterRepositoryAdapter implements CharacterRepository {

	private final CharacterJpaRepository characterJpaRepository;

	public CharacterRepositoryAdapter(CharacterJpaRepository characterJpaRepository) {
		this.characterJpaRepository = characterJpaRepository;
	}

	@Override
	public @Nullable NamuCharacter findOrNull(@NotNull NextDeterminedDbQuery determinedDbQuery) {
		CharacterJpaEntity characterJpaEntity = characterJpaRepository.findByLevelAndGroupNumberAndType(
			determinedDbQuery.level(),
			determinedDbQuery.groupNumber(),
			determinedDbQuery.characterType()
		);
		return getNamuCharacter(characterJpaEntity);
	}

	@Override
	public @Nullable NamuCharacter findRandomOrNull(@NotNull NextRandomCharacterDbQuery randomDbQuery) {
		CharacterJpaEntity characterJpaEntity = characterJpaRepository.findRandomBy(
			randomDbQuery.level(),
			randomDbQuery.groupNumber(),
			randomDbQuery.isEndangered(),
			randomDbQuery.characterType().name()
		);
		return getNamuCharacter(characterJpaEntity);
	}

	private @Nullable NamuCharacter getNamuCharacter(CharacterJpaEntity characterJpaEntity) {
		if (characterJpaEntity != null) {
			return characterJpaEntity.toNamuCharacter();
		}
		return null;
	}
}
