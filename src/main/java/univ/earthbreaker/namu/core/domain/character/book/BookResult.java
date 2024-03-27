package univ.earthbreaker.namu.core.domain.character.book;

import java.util.List;

public record BookResult(
	int totalAcquiredCount,
	List<BookSectionResult> sectionResults
) {
}
