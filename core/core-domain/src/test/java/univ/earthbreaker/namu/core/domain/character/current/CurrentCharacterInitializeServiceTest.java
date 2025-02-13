package univ.earthbreaker.namu.core.domain.character.current;

import static org.mockito.Mockito.*;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CurrentCharacterInitializeServiceTest {

	private @Mock CurrentCharacterFinder currentCharacterFinder;
	private @Mock CurrentCharacterInitializer currentCharacterInitializer;
	private @InjectMocks CurrentCharacterInitializeService currentCharacterInitializeService;

	@DisplayName("회원의 번호를 받아, 현재 캐릭터를 초기 단계의 캐릭터로 초기화 할 수 있다")
	@Test
	void initialize() {
	    // given
		when(currentCharacterFinder.find(MEMBER_NO))
			.thenReturn(FINAL_CURRENT_CHARACTER);

	    // when
		currentCharacterInitializeService.initialize(MEMBER_NO);

	    // then
		verify(currentCharacterInitializer).initialize(FINAL_CURRENT_CHARACTER);
	}
}
