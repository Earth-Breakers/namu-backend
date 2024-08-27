package univ.earthbreaker.namu.core.api.character;

import univ.earthbreaker.namu.core.domain.character.CharacterType;

public class CharacterBookViewReader {

	private CharacterBookViewReader() {
	}

	static CharacterBookResponse.BookSectionResponse readByType(CharacterBookView book, CharacterType type) {
		return new CharacterBookResponse.BookSectionResponse(
			type,
			book.readTotalCountOfType(type),
			book.readAcquiredCharacterCountByType(type),
			book.readCharactersByType(type)
		);
	}
}
