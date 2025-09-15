package univ.earthbreaker.namu.core.service.character.current;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.character.current.CurrentCharacter;
import univ.earthbreaker.namu.core.domain.character.current.infra.CurrentCharacterRepository;

@Component
public class CurrentCharacterRegister {

	private final CurrentCharacterRepository currentCharacterRepository;

	public CurrentCharacterRegister(CurrentCharacterRepository currentCharacterRepository) {
		this.currentCharacterRepository = currentCharacterRepository;
	}

	@NotNull CurrentCharacter register(long memberNo) {
		return currentCharacterRepository.register(memberNo);
	}
}
