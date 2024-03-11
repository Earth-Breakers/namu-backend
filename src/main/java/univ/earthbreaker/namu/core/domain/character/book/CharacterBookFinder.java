package univ.earthbreaker.namu.core.domain.character.book;

import org.springframework.stereotype.Component;

@Component
public class CharacterBookFinder {

	private final CharacterBookRepository characterBookRepository;

	public CharacterBookFinder(CharacterBookRepository characterBookRepository) {
		this.characterBookRepository = characterBookRepository;
	}

	CharacterBook find(long memberNo) {
		return characterBookRepository.find(memberNo);
	}
}
