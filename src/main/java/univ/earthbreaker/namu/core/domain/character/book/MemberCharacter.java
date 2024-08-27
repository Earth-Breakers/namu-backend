package univ.earthbreaker.namu.core.view.character;

import univ.earthbreaker.namu.core.domain.character.CharacterType;
import univ.earthbreaker.namu.core.domain.character.Gender;
import univ.earthbreaker.namu.core.domain.character.NamuCharacter;

public class MemberCharacter {

	private final long no;
	private final long memberNo;
	private final int count;
	private final NamuCharacter character;
	private final boolean isAcquired;

	public MemberCharacter(long no, long memberNo, int count, NamuCharacter character, boolean isAcquired) {
		this.no = no;
		this.memberNo = memberNo;
		this.count = count;
		this.character = character;
		this.isAcquired = isAcquired;
	}

	public int getCount() {
		return count;
	}

	public long getCharacterNo() {
		return character.getNo();
	}

	public Gender getGender() {
		return character.getGender();
	}

	public boolean isEndangered() {
		return character.isEndangered();
	}

	public String getDescription() {
		return character.getDescription();
	}

	public CharacterType getType() {
		return character.getType();
	}

	public String getName() {
		return character.getName();
	}

	public String getDetailImagePath() {
		return character.getDetailImagePath();
	}
}
