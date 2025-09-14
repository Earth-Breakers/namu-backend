package univ.earthbreaker.namu.core.domain.character.current;

import java.util.Objects;

import univ.earthbreaker.namu.core.domain.character.NamuCharacter;

public class TargetCharacter {

	private final long characterNo;
	private final int groupNumber;
	private final String name;
	private final String mainImagePath;
	private final String backgroundImagePath;
	private final String scripts;

	public TargetCharacter(
		long characterNo,
		int groupNumber,
		String name,
		String mainImagePath,
		String backgroundImagePath,
		String scripts
	) {
		this.characterNo = characterNo;
		this.groupNumber = groupNumber;
		this.name = name;
		this.mainImagePath = mainImagePath;
		this.backgroundImagePath = backgroundImagePath;
		this.scripts = scripts;
	}

	static TargetCharacter changeTo(NamuCharacter namuCharacter) {
		return new TargetCharacter(
			namuCharacter.getNo(),
			namuCharacter.getGroupNumber(),
			namuCharacter.getName(),
			namuCharacter.getMainImagePath(),
			namuCharacter.getBackgroundImagePath(),
			namuCharacter.getScripts()
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

	String getBackgroundImagePath() {
		return backgroundImagePath;
	}

	String getScripts() {
		return scripts;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		TargetCharacter that = (TargetCharacter)o;
		return characterNo == that.characterNo && groupNumber == that.groupNumber && name.equals(that.name)
			&& mainImagePath.equals(that.mainImagePath) && backgroundImagePath.equals(that.backgroundImagePath)
			&& scripts.equals(that.scripts);
	}

	@Override
	public int hashCode() {
		return Objects.hash(characterNo, groupNumber, name, mainImagePath, backgroundImagePath, scripts);
	}
}
