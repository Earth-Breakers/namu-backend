package univ.earthbreaker.namu.core.domain.character.book;

import org.springframework.stereotype.Component;

@Component
public class CharacterBookFinder {

	private final MemberCharacterRepository memberCharacterRepository;

	public CharacterBookFinder(MemberCharacterRepository memberCharacterRepository) {
		this.memberCharacterRepository = memberCharacterRepository;
	}

	CharacterBook find(long memberNo) {
		return memberCharacterRepository.findBook(memberNo);
	}
}
