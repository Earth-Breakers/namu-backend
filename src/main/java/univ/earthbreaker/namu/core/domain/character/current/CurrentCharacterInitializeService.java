package univ.earthbreaker.namu.core.domain.character.current;

import org.springframework.stereotype.Service;

@Service
public class CurrentCharacterInitializeService {

	private final CurrentCharacterFinder currentCharacterFinder;
	private final CurrentCharacterInitializer currentCharacterInitializer;

	public CurrentCharacterInitializeService(
		CurrentCharacterFinder currentCharacterFinder,
		CurrentCharacterInitializer currentCharacterInitializer
	) {
		this.currentCharacterFinder = currentCharacterFinder;
		this.currentCharacterInitializer = currentCharacterInitializer;
	}

	public void initialize(long memberNo) {
		CurrentCharacter currentCharacter = currentCharacterFinder.find(memberNo);
		CurrentCharacterValidator.validateLevelIsFinal(currentCharacter);
		currentCharacterInitializer.initialize(currentCharacter);
	}
}
