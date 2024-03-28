package univ.earthbreaker.namu.core.domain.character.current;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class CurrentCharacterFinder {

	private final CurrentCharacterRepository currentCharacterRepository;

	public CurrentCharacterFinder(CurrentCharacterRepository currentCharacterRepository) {
		this.currentCharacterRepository = currentCharacterRepository;
	}

	public @NotNull CurrentCharacter find(long memberNo) {
		CurrentCharacter currentCharacter = currentCharacterRepository.findOrNull(memberNo);
		if (currentCharacter != null) {
			return currentCharacter;
		}
		throw CurrentCharacterNotFoundException.notFound(memberNo);
	}
}
