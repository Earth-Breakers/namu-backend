package univ.earthbreaker.namu.core.domain.character;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

public class TargetCharacter {

	private final long characterNo;
	private final int groupNumber;
	private final String mainImagePath;

	TargetCharacter(long characterNo, int groupNumber, String mainImagePath) {
		this.characterNo = characterNo;
		this.groupNumber = groupNumber;
		this.mainImagePath = mainImagePath;
	}

	static @NotNull TargetCharacter changeTo(@NotNull NamuCharacter namuCharacter) {
		return new TargetCharacter(
			namuCharacter.getNo(),
			namuCharacter.getGroupNumber(),
			namuCharacter.getMainImagePath()
		);
	}

	long getCharacterNo() {
		return characterNo;
	}

	int getGroupNumber() {
		return groupNumber;
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
		return characterNo == that.characterNo && groupNumber == that.groupNumber && mainImagePath.equals(
			that.mainImagePath);
	}

	@Override
	public int hashCode() {
		return Objects.hash(characterNo, groupNumber, mainImagePath);
	}
}
