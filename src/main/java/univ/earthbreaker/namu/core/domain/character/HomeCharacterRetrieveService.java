package univ.earthbreaker.namu.core.domain.character;

import org.springframework.stereotype.Service;

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
