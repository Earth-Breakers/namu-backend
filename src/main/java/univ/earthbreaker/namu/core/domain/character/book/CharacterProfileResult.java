package univ.earthbreaker.namu.core.domain.character.book;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.common.Constant;

public record CharacterProfileResult(
	long characterNo,
	String thumbnailImageUrl,
	boolean isAcquired
) {
	static @NotNull CharacterProfileResult of(long characterNo, String thumbnailImagePath, boolean isAcquired) {
		return new CharacterProfileResult(
			characterNo,
			Constant.IMAGE_ACCESS_URL + thumbnailImagePath,
			isAcquired
		);
	}
}
