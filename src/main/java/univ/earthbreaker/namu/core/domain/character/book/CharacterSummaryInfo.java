package univ.earthbreaker.namu.core.domain.character.book;

import java.util.Objects;

public class CharacterSummaryInfo {

	private final long characterNo;
	private final int count;
	private final String thumbnailImagePath;
	private final boolean isAcquired;

	CharacterSummaryInfo(long characterNo, int count, String thumbnailImagePath, boolean isAcquired) {
		this.characterNo = characterNo;
		this.count = count;
		this.thumbnailImagePath = thumbnailImagePath;
		this.isAcquired = isAcquired;
	}

	CharacterProfileResult toProfileResult() {
		return new CharacterProfileResult(characterNo, thumbnailImagePath, isAcquired);
	}

	boolean isAcquired() {
		return isAcquired;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CharacterSummaryInfo that = (CharacterSummaryInfo)o;
		return characterNo == that.characterNo && count == that.count && isAcquired == that.isAcquired
			&& thumbnailImagePath.equals(that.thumbnailImagePath);
	}

	@Override
	public int hashCode() {
		return Objects.hash(characterNo, count, thumbnailImagePath, isAcquired);
	}
}
