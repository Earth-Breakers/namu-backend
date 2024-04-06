package univ.earthbreaker.namu.core.domain.character.book;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.character.CharacterType;

public class CharacterBookReader {

	private CharacterBookReader() {
	}

	static @NotNull BookSectionResult readByType(@NotNull CharacterBook book, CharacterType type) {
		return new BookSectionResult(
			type,
			book.readTotalCountOfType(type),
			book.readAcquiredCharacterCountByType(type),
			book.readCharactersByType(type)
		);
	}
}
