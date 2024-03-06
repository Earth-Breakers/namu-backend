package univ.earthbreaker.namu.core.domain.character;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository {

	@Nullable NamuCharacter findOrNull(NextDeterminedRequestDto requestDto);

	@Nullable NamuCharacter findRandomOrNull(NextRandomCharacterRequestDto requestDto);
}
