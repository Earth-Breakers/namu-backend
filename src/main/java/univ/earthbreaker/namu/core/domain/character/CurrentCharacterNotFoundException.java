package univ.earthbreaker.namu.core.domain.character;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.common.NotFoundException;

public class CurrentCharacterNotFoundException extends NotFoundException {

	private CurrentCharacterNotFoundException(String domainName) {
		super(domainName);
	}

	static @NotNull CurrentCharacterNotFoundException notFound(long memberNo) {
		return new CurrentCharacterNotFoundException(String.format("회원 %d 의 현재 캐릭터가 존재하지 않습니다", memberNo));
	}
}
