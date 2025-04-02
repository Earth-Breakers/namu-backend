package univ.earthbreaker.namu.core.api.character;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.character.CharacterType;
import univ.earthbreaker.namu.core.domain.common.Constant;

public record CharacterBookResponse(
	int totalAcquiredCount,
	List<BookSectionResponse> sectionResponses
) {
	record BookSectionResponse(
		CharacterType type,
		int totalCountOfType,
		int acquiredCount,
		List<ProfileResponse> profileResponses
	) {
	}

	record ProfileResponse(
		long characterNo,
		String thumbnailImageUrl,
		boolean isAcquired
	) {
		static @NotNull ProfileResponse of(long characterNo, String thumbnailImagePath, boolean isAcquired) {
			return new ProfileResponse(
				characterNo,
				Constant.IMAGE_ACCESS_URL + thumbnailImagePath,
				isAcquired
			);
		}
	}
}
