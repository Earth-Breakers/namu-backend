package univ.earthbreaker.namu.core.domain.character.current;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.common.BadRequestException;

public class CurrentCharacterBadRequestException extends BadRequestException {

	private CurrentCharacterBadRequestException(String message) {
		super(message);
	}

	static @NotNull CurrentCharacterBadRequestException shouldBeInitial() {
		return new CurrentCharacterBadRequestException("현재 캐릭터의 타입은 INITIAL 이어야 합니다");
	}

	static @NotNull CurrentCharacterBadRequestException missMatch() {
		return new CurrentCharacterBadRequestException("현재 캐릭터에 맞는 에너지 타입이 아닙니다");
	}

	static @NotNull CurrentCharacterBadRequestException expOverflow() {
		return new CurrentCharacterBadRequestException("현재 캐릭터의 필요 에너지 이상의 에너지가 공급되었습니다");
	}
}
