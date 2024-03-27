package univ.earthbreaker.namu.core.domain.character.book;

import java.util.List;
import java.util.Objects;

import univ.earthbreaker.namu.core.domain.character.CharacterType;

public class CharacterBook {

	private final long no;
	private final long masterMemberNo;
	private final Characters characters;

	public CharacterBook(long no, long masterMemberNo, Characters characters) {
		this.no = no;
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CharacterBook that = (CharacterBook)o;
		return no == that.no && masterMemberNo == that.masterMemberNo && characters.equals(that.characters);
	}

	@Override
	public int hashCode() {
		return Objects.hash(no, masterMemberNo, characters);
	}
}
