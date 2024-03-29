package univ.earthbreaker.namu.core.domain.character.book;

import java.util.List;

import univ.earthbreaker.namu.core.domain.character.CharacterType;

public class CharacterBook {

	private final long masterMemberNo;
	private final Characters characters;

	public CharacterBook(long masterMemberNo, Characters characters) {
		this.masterMemberNo = masterMemberNo;
		this.characters = characters;
	}

	int readTotalAcquiredCharacterCount() {
		return characters.countAllAcquired();
	}

	int readAcquiredCharacterCountByType(CharacterType type) {
		return characters.countAllAcquiredByType(type);
	}

	int readTotalCountOfType(CharacterType type) {
		return characters.countTotalCountOfType(type);
	}

	List<CharacterProfileResult> readCharactersByType(CharacterType type) {
		return characters.showProfileByType(type);
	}
}
