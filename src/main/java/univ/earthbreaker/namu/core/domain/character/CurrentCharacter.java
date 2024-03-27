package univ.earthbreaker.namu.core.domain.character;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

public class CurrentCharacter {

	private final Master master;
	private final TargetCharacter character;
	private final CharacterType characterType;
	private final CharacterStatus status;

	CurrentCharacter(
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

	CurrentCharacter giveInitialEnergyExp(@NotNull CharacterType givenEnergyType, int energyExp) {
		if (characterType.isInitial()) {
			return new CurrentCharacter(master, character, givenEnergyType, status.addExp(energyExp));
		}
		throw CurrentCharacterBadRequestException.shouldBeInitial();
	}

	CurrentCharacter giveEnergyExp(@NotNull CharacterType givenEnergyType, int energyExp) {
		if (characterType.equals(givenEnergyType)) {
			return new CurrentCharacter(master, character, givenEnergyType, status.addExp(energyExp));
		}
		throw CurrentCharacterBadRequestException.missMatch();
	}

	CurrentCharacter growToNext(@NotNull NamuCharacter namuCharacter) {
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

	int calculateExpectedNextLevel() {
		int expectLevelAfterUp = status.calculateToNextLevelValue();
		if (status.isExpectLevelOverFlow(expectLevelAfterUp)) {
			throw new IllegalStateException("잘못된 요청으로 현재 혀용하는 level 의 최대치를 초과했습니다");
		}
		return expectLevelAfterUp;
	}

	boolean cannotLevelUp() {
		return !status.isCanLevelUp();
	}

	boolean levelIsNotMiddle() {
		return !status.isLevelMiddle();
	}

	boolean levelIsNotBegin() {
		return !status.isLevelBegin();
	}

	public static @NotNull CurrentCharacter of(
		long memberNo,
		long characterNo,
		CharacterType energyType,
		int level,
		int requiredExp,
		int currentExp,
		int groupNumber,
		String name,
		String mainImagePath
	) {
		return new CurrentCharacter(
			new Master(memberNo),
			new TargetCharacter(characterNo, groupNumber, name, mainImagePath),
			energyType,
			CharacterStatus.of(level, requiredExp, currentExp)
		);
	}

	public static @NotNull CurrentCharacter initialize(
		long memberNo,
		long characterNo,
		int requiredExp,
		int groupNumber,
		String name,
		String mainImagePath
	) {
		return new CurrentCharacter(
			new Master(memberNo),
			new TargetCharacter(characterNo, groupNumber, name, mainImagePath),
			CharacterType.INITIAL,
			CharacterStatus.initialize(requiredExp)
		);
	}

	public static @NotNull CurrentCharacter createNext(
		long memberNo,
		long characterNo,
		CharacterType energyType,
		int level,
		int requiredExp,
		int groupNumber,
		String name,
		String mainImagePath
	) {
		return new CurrentCharacter(
			new Master(memberNo),
			new TargetCharacter(characterNo, groupNumber, name, mainImagePath),
			energyType,
			CharacterStatus.createNext(level, requiredExp)
		);
	}

	int getCharacterGroupNumber() {
		return character.getGroupNumber();
	}

	CharacterType getCharacterType() {
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
