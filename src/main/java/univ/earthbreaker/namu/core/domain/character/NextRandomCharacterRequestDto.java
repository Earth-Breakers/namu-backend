package univ.earthbreaker.namu.core.domain.character;

public record NextRandomCharacterRequestDto(
	int level,
	int groupNumber,
	boolean isEndangered,
	CharacterType characterType
) {
}
