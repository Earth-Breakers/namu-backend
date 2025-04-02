package univ.earthbreaker.namu.core.domain.character.current;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CurrentCharacterInitializer {

	private final CurrentCharacterRepository currentCharacterRepository;

	public CurrentCharacterInitializer(CurrentCharacterRepository currentCharacterRepository) {
		this.currentCharacterRepository = currentCharacterRepository;
	}

	@Transactional
	public void initialize(@NotNull CurrentCharacter currentCharacter) {
		currentCharacterRepository.updateToInitial(currentCharacter.getMasterNo());
	}
}
