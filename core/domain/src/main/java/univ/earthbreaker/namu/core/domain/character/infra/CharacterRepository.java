package univ.earthbreaker.namu.core.domain.character.infra;

import org.jetbrains.annotations.Nullable;

import univ.earthbreaker.namu.core.domain.character.NamuCharacter;

public interface CharacterRepository {

	@Nullable NamuCharacter findOrNull(NextDeterminedDbQuery requestDto);

	@Nullable NamuCharacter findRandomOrNull(NextRandomCharacterDbQuery requestDto);
}
