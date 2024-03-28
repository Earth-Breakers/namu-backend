package univ.earthbreaker.namu.core.domain.character.current;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class CurrentCharacterInitializer {

	private final CurrentCharacterRepository currentCharacterRepository;

	public CurrentCharacterInitializer(CurrentCharacterRepository currentCharacterRepository) {
		this.currentCharacterRepository = currentCharacterRepository;
	}

	@NotNull CurrentCharacter initialize(long memberNo) {
		return currentCharacterRepository.updateToInitial(memberNo);
	}
}
