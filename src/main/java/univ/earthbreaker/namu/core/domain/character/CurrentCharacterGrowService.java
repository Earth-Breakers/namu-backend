package univ.earthbreaker.namu.core.domain.character;

import org.springframework.stereotype.Service;

@Service
public class CurrentCharacterGrowService {

	private final CurrentCharacterGrower currentCharacterGrower;

	public CurrentCharacterGrowService(CurrentCharacterGrower currentCharacterGrower) {
		this.currentCharacterGrower = currentCharacterGrower;
	}

	public CurrentCharacterGrowResult growToNextLevel(long memberNo) {
		CurrentCharacter currentCharacter = currentCharacterGrower.growToNext(memberNo);
		return CurrentCharacterGrowResult.from(currentCharacter);
	}

	public CurrentCharacterGrowResult growToNextRandom(long memberNo) {
		CurrentCharacter currentCharacter = currentCharacterGrower.growToRandom(memberNo);
		return CurrentCharacterGrowResult.from(currentCharacter);
	}
}
