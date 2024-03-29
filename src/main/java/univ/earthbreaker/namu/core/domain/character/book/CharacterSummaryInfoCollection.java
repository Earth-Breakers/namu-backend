package univ.earthbreaker.namu.core.domain.character.book;

import java.util.List;
import java.util.Objects;

public class CharacterSummaryInfoCollection {

	private final int totalCountOfType;
	private final List<CharacterSummaryInfo> values;

	CharacterSummaryInfoCollection(int totalCountOfType, List<CharacterSummaryInfo> values) {
		this.totalCountOfType = totalCountOfType;
		this.values = values;
	}

	int countAcquired() {
		return (int)values.stream()
			.filter(CharacterSummaryInfo::isAcquired)
			.count();
	}

	List<CharacterProfileResult> getCharacterProfiles() {
		return values.stream()
			.map(CharacterSummaryInfo::toProfileResult)
			.toList();
	}

	int getTotalCountOfType() {
		return totalCountOfType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CharacterSummaryInfoCollection that = (CharacterSummaryInfoCollection)o;
		return totalCountOfType == that.totalCountOfType && values.equals(that.values);
	}

	@Override
	public int hashCode() {
		return Objects.hash(totalCountOfType, values);
	}
}
