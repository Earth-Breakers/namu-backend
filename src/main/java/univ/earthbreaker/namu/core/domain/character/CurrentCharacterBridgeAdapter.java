package univ.earthbreaker.namu.core.domain.character;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.pushnotification.CharacterQuery;
import univ.earthbreaker.namu.core.domain.pushnotification.CurrentCharacterBridge;

@Component
public class CurrentCharacterBridgeAdapter implements CurrentCharacterBridge {

	private final CurrentCharacterFinder currentCharacterFinder;

	public CurrentCharacterBridgeAdapter(CurrentCharacterFinder currentCharacterFinder) {
		this.currentCharacterFinder = currentCharacterFinder;
	}

	@Override
	public @NotNull CharacterQuery findCurrentCharacter(long memberNo) {
		CurrentCharacter currentCharacter = currentCharacterFinder.find(memberNo);
		return new CharacterQuery(currentCharacter.getCharacterName());
	}
}
