package univ.earthbreaker.namu.core.domain.character;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class CurrentCharacterGrower {

	private final NamuCharacterFinder namuCharacterFinder;
	private final CurrentCharacterFinder currentCharacterFinder;
	private final CurrentCharacterRepository currentCharacterRepository;
	private final EndangeredProbabilityPolicy endangeredProbabilityPolicy;

	public CurrentCharacterGrower(
		NamuCharacterFinder namuCharacterFinder,
		CurrentCharacterFinder currentCharacterFinder,
		CurrentCharacterRepository currentCharacterRepository,
		EndangeredProbabilityPolicy endangeredProbabilityPolicy
	) {
		this.namuCharacterFinder = namuCharacterFinder;
		this.currentCharacterFinder = currentCharacterFinder;
		this.currentCharacterRepository = currentCharacterRepository;
		this.endangeredProbabilityPolicy = endangeredProbabilityPolicy;
	}

	/**
	 * 현재 캐릭터 LEVEL 을 MIDDLE -> END 로 성장시키는 메서드.
	 * @param memberNo 회원 번호
	 * @return LEVEL.END 로 성장한 캐릭터
	 */
	@NotNull CurrentCharacter growToNext(long memberNo) {
		CurrentCharacter currentCharacter = currentCharacterFinder.find(memberNo);
		CurrentCharacterValidator.validateCanLevelUp(currentCharacter);
		CurrentCharacterValidator.validateLevelIsMiddle(currentCharacter);
		NamuCharacter namuCharacter = namuCharacterFinder.findNext(
			currentCharacter.calculateExpectedNextLevel(),
			currentCharacter.getCharacterGroupNumber(),
			currentCharacter.getCharacterType()
		);
		return updateAndGetCurrentCharacter(currentCharacter, namuCharacter);
	}

	/**
	 * 현재 캐릭터 LEVEL 을 BEGIN -> MIDDLE 로 성장시키는 메서드.
	 * 사용자가 주입한 에너지의 종류가 캐릭터 타입을 결정하고,
	 * 해당 캐릭터 타입에 맞는 랜덤한 LEVEL.MIDDLE 캐릭터로 성장한다.
	 * 멸종 위기종이 확률 정책에 맞도록 선택된다.
	 * @param memberNo 회원 번호
	 * @return LEVEL.MIDDLE 로 성장한 캐릭터
	 */
	@NotNull CurrentCharacter growToRandom(long memberNo) {
		CurrentCharacter currentCharacter = currentCharacterFinder.find(memberNo);
		CurrentCharacterValidator.validateCanLevelUp(currentCharacter);
		CurrentCharacterValidator.validateLevelIsBegin(currentCharacter);
		NamuCharacter randomNamuCharacter = namuCharacterFinder.findRandom(
			currentCharacter.calculateExpectedNextLevel(),
			currentCharacter.getCharacterGroupNumber(),
			endangeredProbabilityPolicy.determineEndangered(),
			currentCharacter.getCharacterType()
		);
		return updateAndGetCurrentCharacter(currentCharacter, randomNamuCharacter);
	}

	private CurrentCharacter updateAndGetCurrentCharacter(
		@NotNull CurrentCharacter currentCharacter,
		NamuCharacter namuCharacter
	) {
		CurrentCharacter grownCurrentCharacter = currentCharacter.growToNext(namuCharacter);
		currentCharacterRepository.update(grownCurrentCharacter);
		return grownCurrentCharacter;
	}
}
