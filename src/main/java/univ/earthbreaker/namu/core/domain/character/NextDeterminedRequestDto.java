package univ.earthbreaker.namu.core.domain.character;

public record NextDeterminedRequestDto(
	int level,
	int groupNumber,
	CharacterType characterType
) {
}
