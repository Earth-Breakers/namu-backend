package univ.earthbreaker.namu.core.domain.character.book;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import univ.earthbreaker.namu.core.domain.character.CharacterType;

@Service
public class CharacterBookReadService {

	private final CharacterBookFinder characterBookFinder;

	public CharacterBookReadService(CharacterBookFinder characterBookFinder) {
		this.characterBookFinder = characterBookFinder;
	}

	public BookResult readAll(long memberNo) {
		CharacterBook characterBook = characterBookFinder.find(memberNo);
		List<BookSectionResult> bookSectionResults = Stream.of(
			CharacterBookReader.readByType(characterBook, CharacterType.BEAUTY),
			CharacterBookReader.readByType(characterBook, CharacterType.PURIFY),
			CharacterBookReader.readByType(characterBook, CharacterType.VITALITY)
		).toList();
		return new BookResult(characterBook.readTotalAcquiredCharacterCount(), bookSectionResults);
	}
}
