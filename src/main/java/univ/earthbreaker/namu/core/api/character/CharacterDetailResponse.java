package univ.earthbreaker.namu.core.api.character;

import static univ.earthbreaker.namu.core.api.Constant.IMAGE_ACCESS_URL;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.character.CharacterType;
import univ.earthbreaker.namu.core.domain.character.Gender;
import univ.earthbreaker.namu.core.domain.character.book.MemberCharacter;

public record CharacterDetailResponse(
	long characterNo,
	int count,
	boolean isEndangered,
	CharacterType type,
	Gender gender,
	String name,
	String description,
	String imageUrl
) {
	static @NotNull CharacterDetailResponse from(@NotNull MemberCharacter memberCharacter) {
		return new CharacterDetailResponse(
			memberCharacter.getCharacterNo(),
			memberCharacter.getCount(),
			memberCharacter.isEndangered(),
			memberCharacter.getType(),
			memberCharacter.getGender(),
			memberCharacter.getName(),
			memberCharacter.getDescription(),
			System.getProperty(IMAGE_ACCESS_URL) + memberCharacter.getMainImagePath()
		);
	}
}
