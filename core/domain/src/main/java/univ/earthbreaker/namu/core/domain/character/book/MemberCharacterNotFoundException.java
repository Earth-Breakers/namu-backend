package univ.earthbreaker.namu.core.domain.character.book;

import univ.earthbreaker.namu.core.domain.common.NotFoundException;

public class MemberCharacterNotFoundException extends NotFoundException {

	private MemberCharacterNotFoundException(String domainName) {
		super(domainName);
	}

	public static MemberCharacterNotFoundException notFound(long characterNo) {
		return new MemberCharacterNotFoundException(String.format("캐릭터 %s 의 도감", characterNo));
	}
}
