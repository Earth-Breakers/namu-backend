package univ.earthbreaker.namu.core.domain.character;

import org.jetbrains.annotations.NotNull;

public record CurrentCharacterGrowResult(
	long memberNo,
	String characterType,
	long characterNo,
	int groupNumber,
	int level,
	int requiredExp,
	int currentExp,
	String mainImagePath
) {
	static @NotNull CurrentCharacterGrowResult from(@NotNull CurrentCharacter currentCharacter) {
		return new CurrentCharacterGrowResult(
			currentCharacter.getMasterNo(),
			currentCharacter.getCharacterType().name(),
			currentCharacter.getTargetCharacterNo(),
			currentCharacter.getCharacterGroupNumber(),
			currentCharacter.getStatusLevel(),
			currentCharacter.getStatusRequiredExp(),
			currentCharacter.getStatusCurrentExp(),
			currentCharacter.getTargetCharacterMainImage()
		);
	}
}
