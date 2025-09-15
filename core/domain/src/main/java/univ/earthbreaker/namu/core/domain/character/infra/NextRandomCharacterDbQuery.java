package univ.earthbreaker.namu.core.domain.character.infra;

import univ.earthbreaker.namu.core.domain.character.CharacterType;

public record NextRandomCharacterDbQuery(
	int level,
	int groupNumber,
	boolean isEndangered,
	CharacterType characterType
) {
}
