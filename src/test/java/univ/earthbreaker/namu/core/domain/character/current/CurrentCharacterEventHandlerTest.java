package univ.earthbreaker.namu.core.domain.character.current;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.BEGIN_CURRENT_CHARACTER;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.BEGIN_REQUIRED_EXP;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.CHARACTER_TYPE;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.MEMBER_NO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import univ.earthbreaker.namu.event.character.AddEnergyPointEvent;

@ExtendWith(MockitoExtension.class)
class CurrentCharacterEventHandlerTest {

	private @Mock CurrentCharacterFinder currentCharacterFinder;
	private @Mock CurrentCharacterRepository currentCharacterRepository;
	private @InjectMocks CurrentCharacterEventHandler currentCharacterEventHandler;

	@DisplayName("추가할 에너지 포인트 이벤트를 받아, 현재 캐릭터에게 에너지 포인트를 제공하고 그 값을 갱신한다")
	@Test
	void giveEnergyToCurrentCharacter() {
	    // given
		when(currentCharacterFinder.find(MEMBER_NO))
			.thenReturn(BEGIN_CURRENT_CHARACTER);
		AddEnergyPointEvent event = new AddEnergyPointEvent(MEMBER_NO, BEGIN_REQUIRED_EXP, CHARACTER_TYPE.name());

		// when
		currentCharacterEventHandler.giveEnergyToCurrentCharacter(event);

	    // then
		verify(currentCharacterRepository).update(any(CurrentCharacter.class));
	}
}
