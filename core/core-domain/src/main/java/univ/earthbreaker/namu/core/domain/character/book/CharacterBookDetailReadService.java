package univ.earthbreaker.namu.core.domain.character.book;

import org.springframework.stereotype.Service;

@Service
public class CharacterBookDetailReadService {

	private final MemberCharacterFinder memberCharacterFinder;

	public CharacterBookDetailReadService(MemberCharacterFinder memberCharacterFinder) {
		this.memberCharacterFinder = memberCharacterFinder;
	}

	public MemberCharacter retrieveDetail(long memberNo, long characterNo) {
		return memberCharacterFinder.find(memberNo, characterNo);
	}
}
