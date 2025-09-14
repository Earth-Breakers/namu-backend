package univ.earthbreaker.namu.core.service.character.current;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import univ.earthbreaker.namu.core.domain.character.current.CurrentCharacter;
import univ.earthbreaker.namu.core.domain.character.current.infra.CurrentCharacterRepository;
import univ.earthbreaker.namu.core.service.character.CharacterFixture;

@ExtendWith(MockitoExtension.class)
class CurrentCharacterInitializerTest {

	private @Mock CurrentCharacterRepository currentCharacterRepository;
	private @InjectMocks CurrentCharacterInitializer currentCharacterInitializer;

	@DisplayName("최종 성장 형태의 캐릭터를 받아, 해당 캐릭터를 초기 형태의 캐릭터로 초기화한다")
	@Test
	void initialize() {
		// given
		CurrentCharacter finalCurrentCharacter = CharacterFixture.FINAL_CURRENT_CHARACTER;

		// when
		currentCharacterInitializer.initialize(finalCurrentCharacter);

		// then
		verify(currentCharacterRepository).updateToInitial(finalCurrentCharacter.getTargetCharacterNo());
	}
}
