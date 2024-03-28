package univ.earthbreaker.namu.core.domain.character.book;

import org.springframework.stereotype.Service;

@Service
public class CharacterBookReadService {

	private final CharacterBookFinder characterBookFinder;

	public CharacterBookReadService(CharacterBookFinder characterBookFinder) {
		this.characterBookFinder = characterBookFinder;
	}

	public BookResult readAll(long memberNo) {
		return CharacterBookReader.read(characterBookFinder.find(memberNo));
	}
}
