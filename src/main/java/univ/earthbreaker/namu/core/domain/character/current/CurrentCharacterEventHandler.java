package univ.earthbreaker.namu.core.domain.character.current;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import univ.earthbreaker.namu.core.domain.character.CharacterType;
import univ.earthbreaker.namu.event.character.AddEnergyPointEvent;

@Component
public class CurrentCharacterEventHandler {

	private final CurrentCharacterFinder currentCharacterFinder;
	private final CurrentCharacterRepository currentCharacterRepository;

	public CurrentCharacterEventHandler(
		CurrentCharacterFinder currentCharacterFinder,
		CurrentCharacterRepository currentCharacterRepository
	) {
		this.currentCharacterFinder = currentCharacterFinder;
		this.currentCharacterRepository = currentCharacterRepository;
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void giveEnergyToCurrentCharacter(@NotNull AddEnergyPointEvent event) {
		CurrentCharacter currentCharacter = currentCharacterFinder.find(event.memberNo());
		CharacterType characterType = CharacterType.valueOf(event.energyType());
		currentCharacterRepository.update(currentCharacter.giveEnergyExp(characterType, event.pointValue()));
	}
}
