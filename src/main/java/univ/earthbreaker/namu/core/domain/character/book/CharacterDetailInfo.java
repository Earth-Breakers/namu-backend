package univ.earthbreaker.namu.core.domain.character.book;

import univ.earthbreaker.namu.core.domain.character.Gender;

public class CharacterDetailInfo {

	private final long characterNo;
	private final Gender gender;
	private final String name;
	private final String description;
	private final String thumbnailImagePath;
	private final String mainImagePath;
	private final boolean isEndangered;

	public CharacterDetailInfo(
		long characterNo,
		Gender gender,
		String name,
		String description,
		String thumbnailImagePath,
		String mainImagePath,
		boolean isEndangered
	) {
		this.characterNo = characterNo;
		this.gender = gender;
		this.name = name;
		this.description = description;
		this.thumbnailImagePath = thumbnailImagePath;
		this.mainImagePath = mainImagePath;
		this.isEndangered = isEndangered;
	}
}
