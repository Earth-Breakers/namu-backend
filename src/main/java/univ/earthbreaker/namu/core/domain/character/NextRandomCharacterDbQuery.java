package univ.earthbreaker.namu.core.domain.character;

public record NextRandomCharacterDbQuery(
	int level,
	int groupNumber,
	boolean isEndangered,
	CharacterType characterType
) {
}
