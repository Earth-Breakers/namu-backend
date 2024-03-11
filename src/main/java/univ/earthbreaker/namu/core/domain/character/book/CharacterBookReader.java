package univ.earthbreaker.namu.core.domain.character.book;

import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.character.CharacterType;

public class CharacterBookReader {

	private CharacterBookReader() {
	}

	static @NotNull BookResult read(@NotNull CharacterBook book) {
		List<BookSectionResult> bookSectionResults = Arrays.stream(CharacterType.values())
			.map(type -> new BookSectionResult(
				type,
				book.readTotalCountOfType(type),
				book.readAcquiredCharacterCountByType(type),
				book.readCharactersByType(type))
			)
			.toList();
		return new BookResult(book.readTotalAcquiredCharacterCount(), bookSectionResults);
	}
}
