package univ.earthbreaker.namu.app.api.character;

import java.util.List;

import univ.earthbreaker.namu.core.domain.character.CharacterType;
import univ.earthbreaker.namu.core.domain.character.book.MemberCharacter;

public class CharacterBookView {

	private final List<MemberCharacter> values;

	public CharacterBookView(List<MemberCharacter> values) {
		this.values = values;
	}

	int readTotalAcquiredCharacterCount() {
		return values.stream()
			.mapToInt(MemberCharacter::getCount)
			.sum();
	}

	int readAcquiredCharacterCountByType(CharacterType type) {
		return (int)values.stream()
			.filter(memberCharacter -> memberCharacter.isSameWithCharacterType(type) && memberCharacter.isAcquired())
			.count();
	}

	int readTotalCountOfType(CharacterType type) {
		return (int)values.stream()
			.filter(memberCharacter -> memberCharacter.isSameWithCharacterType(type))
			.count();
	}

	List<CharacterBookResponse.ProfileResponse> readCharactersByType(CharacterType type) {
		return values.stream()
			.filter(memberCharacter -> memberCharacter.isSameWithCharacterType(type))
			.map(memberCharacter -> CharacterBookResponse.ProfileResponse.of(
				memberCharacter.getCharacterNo(),
				memberCharacter.getThumbnailImagePath(),
				memberCharacter.isAcquired()
			))
			.toList();
	}
}
