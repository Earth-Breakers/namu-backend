package univ.earthbreaker.namu.core.domain.character.current;

import univ.earthbreaker.namu.core.domain.common.NotFoundException;

public class CurrentCharacterNotFoundException extends NotFoundException {

	private CurrentCharacterNotFoundException(String domainName) {
		super(domainName);
	}

	public static CurrentCharacterNotFoundException notFound(long memberNo) {
		return new CurrentCharacterNotFoundException(String.format("회원 %d 의 현재 캐릭터", memberNo));
	}
}
