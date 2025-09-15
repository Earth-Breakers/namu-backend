package univ.earthbreaker.namu.core.domain.character.current;

import java.util.Objects;

import univ.earthbreaker.namu.core.domain.character.CharacterType;
import univ.earthbreaker.namu.core.domain.character.NamuCharacter;

public class CurrentCharacter {

	private final Master master;
	private final TargetCharacter character;
	private final CharacterType characterType;
	private final CharacterStatus status;

	public CurrentCharacter(
		Master master,
		TargetCharacter character,
		CharacterType characterType,
		CharacterStatus status
	) {
		this.master = master;
		this.character = character;
		this.characterType = characterType;
		this.status = status;
	}

	/**
	 * 캐릭터에 에너지 경험치를 추가하고, 초기 상태일 경우 타입을 결정하는 메서드.
	 * 초기 상태에서 주어진 에너지 타입으로 캐릭터의 타입이 결정되며,
	 * 초기 상태가 아닐 경우에는 주어진 에너지 타입이 현재의 캐릭터 타입과 일치해야 합니다.
	 *
	 * @param givenEnergyType 회원이 선택한 에너지 종류
	 * @param energyExp       에너지 포인트
	 * @return 에너지를 받은 현재 캐릭터
	 * @throws CurrentCharacterBadRequestException 타입 불일치 시 예외 발생
	 */
	public CurrentCharacter giveEnergyExp(CharacterType givenEnergyType, int energyExp) {
		if (characterType.isInitial()) {
			return new CurrentCharacter(master, character, givenEnergyType, status.addExp(energyExp));
		} else if (characterType.equals(givenEnergyType)) {
			return new CurrentCharacter(master, character, characterType, status.addExp(energyExp));
		} else {
			throw CurrentCharacterBadRequestException.missMatch();
		}
	}

	public CurrentCharacter growToNext(NamuCharacter namuCharacter) {
		if (!namuCharacter.getType().equals(characterType)) {
			throw new IllegalStateException("캐릭터를 성장시키기 위해서는 캐릭터의 종류가 같아야 합니다");
		}
		return new CurrentCharacter(
			master,
			TargetCharacter.changeTo(namuCharacter),
			characterType,
			CharacterStatus.of(
				namuCharacter.getLevelValue(),
				namuCharacter.getRequiredExp(),
				0
			)
		);
	}

	public int calculateExpectedNextLevel() {
		int expectLevelAfterUp = status.calculateToNextLevelValue();
		if (status.isExpectLevelOverFlow(expectLevelAfterUp)) {
			throw new IllegalStateException("잘못된 요청으로 현재 혀용하는 level 의 최대치를 초과했습니다");
		}
		return expectLevelAfterUp;
	}

	public boolean cannotLevelUp() {
		return !status.isCanLevelUp();
	}

	public boolean levelIsNotFinal() {
		return !status.isLevelFinal();
	}

	public boolean levelIsNotEnd() {
		return !status.isLevelEnd();
	}

	public boolean levelIsNotMiddle() {
		return !status.isLevelMiddle();
	}

	public boolean levelIsNotBegin() {
		return !status.isLevelBegin();
	}

	public static CurrentCharacter of(
		long memberNo,
		long characterNo,
		CharacterType energyType,
		int level,
		int requiredExp,
		int currentExp,
		int groupNumber,
		String name,
		String mainImagePath,
		String backgroundImagePath,
		String scripts
	) {
		return new CurrentCharacter(
			new Master(memberNo),
			new TargetCharacter(characterNo, groupNumber, name, mainImagePath, backgroundImagePath, scripts),
			energyType,
			CharacterStatus.of(level, requiredExp, currentExp)
		);
	}

	public static CurrentCharacter initialize(
		long memberNo,
		long characterNo,
		int requiredExp,
		int groupNumber,
		String name,
		String mainImagePath,
		String backgroundImagePath,
		String scripts
	) {
		return new CurrentCharacter(
			new Master(memberNo),
			new TargetCharacter(characterNo, groupNumber, name, mainImagePath, backgroundImagePath, scripts),
			CharacterType.DEFAULT,
			CharacterStatus.initialize(requiredExp)
		);
	}

	public int getCharacterGroupNumber() {
		return character.getGroupNumber();
	}

	public CharacterType getCharacterType() {
		return characterType;
	}

	public long getMasterNo() {
		return master.getMemberNo();
	}

	public long getTargetCharacterNo() {
		return character.getCharacterNo();
	}

	public String getCharacterName() {
		return character.getName();
	}

	public String getTargetCharacterMainImage() {
		return character.getMainImagePath();
	}

	public String getTargetCharacterBackgroundImage() {
		return character.getBackgroundImagePath();
	}

	public String getTargetCharacterScripts() {
		return character.getScripts();
	}

	public int getStatusLevel() {
		return status.getLevelValue();
	}

	public int getStatusRequiredExp() {
		return status.getRequiredExp();
	}

	public int getStatusCurrentExp() {
		return status.getCurrentExp();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CurrentCharacter that = (CurrentCharacter)o;
		return master.equals(that.master) && character.equals(that.character) && characterType == that.characterType
			&& status.equals(that.status);
	}

	@Override
	public int hashCode() {
		return Objects.hash(master, character, characterType, status);
	}
}
