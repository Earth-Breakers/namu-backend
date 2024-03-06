package univ.earthbreaker.namu.core.domain.character;

import org.springframework.stereotype.Service;

@Service
public class CurrentCharacterGrowService {

	private final CurrentCharacterGrower currentCharacterGrower;

	public CurrentCharacterGrowService(CurrentCharacterGrower currentCharacterGrower) {
		this.currentCharacterGrower = currentCharacterGrower;
	}

	public void growToNextLevel(long memberNo) {
		currentCharacterGrower.growToNext(memberNo);
	}

	public void growToNextRandom(long memberNo) {
		currentCharacterGrower.growToRandom(memberNo);
	}
}
