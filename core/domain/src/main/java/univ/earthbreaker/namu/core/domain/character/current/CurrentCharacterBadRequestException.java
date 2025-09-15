package univ.earthbreaker.namu.core.domain.character.current;

import univ.earthbreaker.namu.core.domain.common.BadRequestException;

public class CurrentCharacterBadRequestException extends BadRequestException {

	private CurrentCharacterBadRequestException(String message) {
		super(message);
	}

	public static CurrentCharacterBadRequestException missMatch() {
		return new CurrentCharacterBadRequestException("현재 캐릭터에 맞는 에너지 타입이 아닙니다");
	}

	public static CurrentCharacterBadRequestException expOverflow() {
		return new CurrentCharacterBadRequestException("현재 캐릭터의 필요 에너지 이상의 에너지가 공급되었습니다");
	}
}
