package univ.earthbreaker.namu.app.api.character;

import static univ.earthbreaker.namu.core.domain.common.Constant.IMAGE_ACCESS_URL;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.character.current.CurrentCharacter;

public record FinalCharacterResponse(
	long characterNo,
	String mainImageUrl,
	String backgroundImageUrl,
	String scripts
) {
	static @NotNull FinalCharacterResponse from(@NotNull CurrentCharacter finalCharacter) {
		return new FinalCharacterResponse(
			finalCharacter.getTargetCharacterNo(),
			IMAGE_ACCESS_URL + finalCharacter.getTargetCharacterMainImage(),
			IMAGE_ACCESS_URL + finalCharacter.getTargetCharacterBackgroundImage(),
			finalCharacter.getTargetCharacterScripts()
		);
	}
}
