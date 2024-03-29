package univ.earthbreaker.namu.core.domain.character;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.common.NotFoundException;

public class NamuCharacterNotFoundException extends NotFoundException {

	private NamuCharacterNotFoundException(String domainName) {
		super(domainName);
	}

	public static @NotNull NamuCharacterNotFoundException notFoundNext() {
		return new NamuCharacterNotFoundException("다음 성장 형태의 캐릭터");
	}

	public static @NotNull NamuCharacterNotFoundException notFoundRandom() {
		return new NamuCharacterNotFoundException("랜덤으로 가져온 캐릭터");
	}
}
