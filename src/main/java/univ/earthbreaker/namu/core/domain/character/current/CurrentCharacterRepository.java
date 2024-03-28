package univ.earthbreaker.namu.core.domain.character.current;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentCharacterRepository {

	@Nullable CurrentCharacter findOrNull(long memberNo);

	@NotNull CurrentCharacter register(long memberNo);

	@NotNull CurrentCharacter updateToInitial(long memberNo);

	void update(CurrentCharacter updatedCurrentCharacter);
}
