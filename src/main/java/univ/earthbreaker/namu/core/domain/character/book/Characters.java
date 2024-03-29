package univ.earthbreaker.namu.core.domain.character.book;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import univ.earthbreaker.namu.core.domain.character.CharacterType;

public class Characters {

	private final Map<CharacterType, CharacterSummaryInfoCollection> values;

	public Characters(Map<CharacterType, CharacterSummaryInfoCollection> values) {
		this.values = values;
	}

	int countAllAcquired() {
		int totalCount = 0;
		for (Map.Entry<CharacterType, CharacterSummaryInfoCollection> entry : values.entrySet()) {
			CharacterSummaryInfoCollection infoCollection = entry.getValue();
			totalCount += infoCollection.countAcquired();
		}
		return totalCount;
	}

	int countAllAcquiredByType(CharacterType type) {
		return getCharacterInfoCollection(type).countAcquired();
	}

	int countTotalCountOfType(CharacterType type) {
		return getCharacterInfoCollection(type).getTotalCountOfType();
	}

	List<CharacterProfileResult> showProfileByType(CharacterType type) {
		return getCharacterInfoCollection(type).getCharacterProfiles();
	}

	private CharacterSummaryInfoCollection getCharacterInfoCollection(CharacterType type) {
		return values.get(type);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Characters that = (Characters)o;
		return values.equals(that.values);
	}

	@Override
	public int hashCode() {
		return Objects.hash(values);
	}
}
