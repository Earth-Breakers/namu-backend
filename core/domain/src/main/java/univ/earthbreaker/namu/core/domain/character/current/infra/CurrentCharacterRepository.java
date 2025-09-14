package univ.earthbreaker.namu.core.domain.character.current.infra;

import univ.earthbreaker.namu.core.domain.character.current.CurrentCharacter;

public interface CurrentCharacterRepository {

	CurrentCharacter findOrNull(long memberNo);

	CurrentCharacter register(long memberNo);

	void updateToInitial(long memberNo);

	void update(CurrentCharacter updatedCurrentCharacter);
}
