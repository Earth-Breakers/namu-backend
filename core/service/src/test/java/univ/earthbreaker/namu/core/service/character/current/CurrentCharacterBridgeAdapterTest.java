package univ.earthbreaker.namu.core.service.character.current;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import univ.earthbreaker.namu.core.domain.pushnotification.CharacterQuery;
import univ.earthbreaker.namu.core.service.character.CharacterFixture;

@ExtendWith(MockitoExtension.class)
class CurrentCharacterBridgeAdapterTest {

	private @Mock CurrentCharacterFinder currentCharacterFinder;
	private @InjectMocks CurrentCharacterBridgeAdapter currentCharacterBridgeAdapter;

	@DisplayName("회원 번호를 받아 해당 회원의 현재 캐릭터의 이름을 반환한다")
	@Test
	void findCurrentCharacter() {
	    // given
		when(currentCharacterFinder.find(CharacterFixture.MEMBER_NO))
			.thenReturn(CharacterFixture.END_CURRENT_CHARACTER);

	    // when
		CharacterQuery actual = currentCharacterBridgeAdapter.findCurrentCharacter(CharacterFixture.MEMBER_NO);

		// then
		assertThat(actual).isNotNull();
		assertThat(actual.name()).isEqualTo(CharacterFixture.END_CURRENT_CHARACTER.getCharacterName());
	}
}
