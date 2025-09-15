package univ.earthbreaker.namu.core.service.character.current;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import univ.earthbreaker.namu.core.domain.character.current.CurrentCharacter;
import univ.earthbreaker.namu.core.domain.character.current.infra.CurrentCharacterRepository;
import univ.earthbreaker.namu.core.service.character.CharacterFixture;
import univ.earthbreaker.namu.event.character.AddEnergyPointEvent;
import univ.earthbreaker.namu.event.character.InitCurrentCharacterEvent;

@ExtendWith(MockitoExtension.class)
class CurrentCharacterEventHandlerTest {

	private @Mock CurrentCharacterFinder currentCharacterFinder;
	private @Mock CurrentCharacterRepository currentCharacterRepository;
	private @InjectMocks CurrentCharacterEventHandler currentCharacterEventHandler;

	@DisplayName("추가할 에너지 포인트 이벤트를 받아, 현재 캐릭터에게 에너지 포인트를 제공하고 그 값을 갱신한다")
	@Test
	void giveEnergyToCurrentCharacter() {
	    // given
		when(currentCharacterFinder.find(CharacterFixture.MEMBER_NO))
			.thenReturn(CharacterFixture.BEGIN_CURRENT_CHARACTER);
		AddEnergyPointEvent event = new AddEnergyPointEvent(
			CharacterFixture.MEMBER_NO, CharacterFixture.BEGIN_REQUIRED_EXP, CharacterFixture.CHARACTER_TYPE.name());

		// when
		currentCharacterEventHandler.giveEnergyToCurrentCharacter(event);

	    // then
		verify(currentCharacterRepository).update(any(CurrentCharacter.class));
	}

	@DisplayName("회원 계정 생성 시 초기 캐릭터 지급 이벤트를 구독하고, 이벤트를 받아 초기 캐릭터를 등록한다")
	@Test
	void registerInitCurrentCharacter() {
	    // given
		InitCurrentCharacterEvent event = new InitCurrentCharacterEvent(1L);

		// when
		currentCharacterEventHandler.registerInitCurrentCharacter(event);

	    // then
		verify(currentCharacterRepository).register(event.memberNo());
	}
}
