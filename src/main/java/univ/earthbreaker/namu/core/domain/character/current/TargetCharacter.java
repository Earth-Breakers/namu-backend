package univ.earthbreaker.namu.core.domain.character.current;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.character.NamuCharacter;

public class TargetCharacter {

	private final long characterNo;
	private final int groupNumber;
	private final String name;
	private final String mainImagePath;

	public TargetCharacter(long characterNo, int groupNumber, String name, String mainImagePath) {
		this.characterNo = characterNo;
		this.groupNumber = groupNumber;
		this.name = name;
		this.mainImagePath = mainImagePath;
	}

	static @NotNull TargetCharacter changeTo(@NotNull NamuCharacter namuCharacter) {
		return new TargetCharacter(
			namuCharacter.getNo(),
			namuCharacter.getGroupNumber(),
			namuCharacter.getName(),
			namuCharacter.getMainImagePath()
		);
	}

	long getCharacterNo() {
		return characterNo;
	}

	int getGroupNumber() {
		return groupNumber;
	}

	String getName() {
		return name;
	}

	String getMainImagePath() {
		return mainImagePath;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		TargetCharacter that = (TargetCharacter)o;
		return characterNo == that.characterNo && groupNumber == that.groupNumber && name.equals(that.name)
			&& mainImagePath.equals(that.mainImagePath);
	}

	@Override
	public int hashCode() {
		return Objects.hash(characterNo, groupNumber, name, mainImagePath);
	}
}
