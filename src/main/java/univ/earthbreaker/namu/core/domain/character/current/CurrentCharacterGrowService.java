package univ.earthbreaker.namu.core.domain.character.current;

import org.springframework.stereotype.Service;

import univ.earthbreaker.namu.event.EventPublisher;
import univ.earthbreaker.namu.event.character.AddCharacterBookEvent;

@Service
public class CurrentCharacterGrowService {

	private final CurrentCharacterGrower currentCharacterGrower;
	private final EventPublisher eventPublisher;

	public CurrentCharacterGrowService(CurrentCharacterGrower currentCharacterGrower, EventPublisher eventPublisher) {
		this.currentCharacterGrower = currentCharacterGrower;
		this.eventPublisher = eventPublisher;
	}

	public void growToEndLevel(long memberNo) {
		currentCharacterGrower.growToEnd(memberNo);
	}

	public void growToMiddleLevel(long memberNo) {
		currentCharacterGrower.growToMiddle(memberNo);
	}

	public void growToFinalRandom(long memberNo) {
		CurrentCharacter finalCurrentCharacter = currentCharacterGrower.growToFinal(memberNo);
		eventPublisher.publish(new AddCharacterBookEvent(memberNo, finalCurrentCharacter.getTargetCharacterNo()));
	}
}
