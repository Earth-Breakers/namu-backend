package univ.earthbreaker.namu.core.domain.character;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository {

	@Nullable NamuCharacter findOrNull(NextDeterminedDbQuery requestDto);

	@Nullable NamuCharacter findRandomOrNull(NextRandomCharacterDbQuery requestDto);
}
