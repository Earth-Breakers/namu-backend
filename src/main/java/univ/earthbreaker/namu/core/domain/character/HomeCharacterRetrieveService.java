package univ.earthbreaker.namu.core.domain.character;

import org.springframework.stereotype.Service;

import univ.earthbreaker.namu.core.domain.character.current.CurrentCharacter;
import univ.earthbreaker.namu.core.domain.character.current.CurrentCharacterFinder;

@Service
public class HomeCharacterRetrieveService {

	private final CurrentCharacterFinder currentCharacterFinder;

	public HomeCharacterRetrieveService(CurrentCharacterFinder currentCharacterFinder) {
		this.currentCharacterFinder = currentCharacterFinder;
	}

	public CurrentCharacter retrieveHomeCharacter(long memberNo) {
		return currentCharacterFinder.find(memberNo);
	}
}
