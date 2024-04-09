package univ.earthbreaker.namu.core.api.character;

import static univ.earthbreaker.namu.core.domain.common.Constant.IMAGE_ACCESS_URL;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.character.current.CurrentCharacter;

public record HomeCharacterResponse(
	String characterType,
	String imageUrl,
	int requiredExp,
	int currentExp
) {
	static @NotNull HomeCharacterResponse from(@NotNull CurrentCharacter currentCharacter) {
		return new HomeCharacterResponse(
			currentCharacter.getCharacterType().name(),
			IMAGE_ACCESS_URL + currentCharacter.getTargetCharacterMainImage(),
			currentCharacter.getStatusRequiredExp(),
			currentCharacter.getStatusCurrentExp()
		);
	}
}
