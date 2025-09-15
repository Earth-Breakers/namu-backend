package univ.earthbreaker.namu.core.service.character.current;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.character.current.CurrentCharacter;
import univ.earthbreaker.namu.core.domain.character.current.CurrentCharacterNotFoundException;
import univ.earthbreaker.namu.core.domain.character.current.infra.CurrentCharacterRepository;

@Component
public class CurrentCharacterFinder {

	private final CurrentCharacterRepository currentCharacterRepository;

	public CurrentCharacterFinder(CurrentCharacterRepository currentCharacterRepository) {
		this.currentCharacterRepository = currentCharacterRepository;
	}

	public CurrentCharacter find(long memberNo) {
		CurrentCharacter currentCharacter = currentCharacterRepository.findOrNull(memberNo);
		if (currentCharacter != null) {
			return currentCharacter;
		}
		throw CurrentCharacterNotFoundException.notFound(memberNo);
	}
}
