package univ.earthbreaker.namu.database.core.character;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

import univ.earthbreaker.namu.core.domain.character.CharacterRepository;
import univ.earthbreaker.namu.core.domain.character.NamuCharacter;
import univ.earthbreaker.namu.core.domain.character.NextDeterminedRequestDto;
import univ.earthbreaker.namu.core.domain.character.NextRandomCharacterRequestDto;

@Repository
public class CharacterRepositoryAdapter implements CharacterRepository {

	private final CharacterJpaRepository characterJpaRepository;

	public CharacterRepositoryAdapter(CharacterJpaRepository characterJpaRepository) {
		this.characterJpaRepository = characterJpaRepository;
	}

	@Override
	public @Nullable NamuCharacter findOrNull(@NotNull NextDeterminedRequestDto requestDto) {
		CharacterJpaEntity characterJpaEntity = characterJpaRepository.findByLevelAndGroupNumberAndType(
			requestDto.level(),
			requestDto.groupNumber(),
			requestDto.characterType()
		);
		return getNamuCharacter(characterJpaEntity);
	}

	@Override
	public @Nullable NamuCharacter findRandomOrNull(@NotNull NextRandomCharacterRequestDto requestDto) {
		CharacterJpaEntity characterJpaEntity = characterJpaRepository.findRandomBy(
			requestDto.level(),
			requestDto.groupNumber(),
			requestDto.isEndangered(),
			requestDto.characterType()
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
