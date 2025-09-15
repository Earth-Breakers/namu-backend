package univ.earthbreaker.namu.core.domain.character.infra;

import univ.earthbreaker.namu.core.domain.character.CharacterType;

public record NextDeterminedDbQuery(
	int level,
	int groupNumber,
	CharacterType characterType
) {
}
