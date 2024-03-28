package univ.earthbreaker.namu.core.domain.character.book;

import java.util.List;

import univ.earthbreaker.namu.core.domain.character.CharacterType;

public record BookSectionResult(
	CharacterType type,
	int totalCountOfType,
	int acquiredCount,
	List<CharacterProfileResult> profileResults
) {
}
