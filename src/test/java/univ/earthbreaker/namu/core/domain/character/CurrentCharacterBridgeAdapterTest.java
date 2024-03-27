package univ.earthbreaker.namu.core.domain.character;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.END_CURRENT_CHARACTER;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.MEMBER_NO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import univ.earthbreaker.namu.core.domain.pushnotification.CharacterQuery;

@ExtendWith(MockitoExtension.class)
class CurrentCharacterBridgeAdapterTest {

	private @Mock CurrentCharacterFinder currentCharacterFinder;
	private @InjectMocks CurrentCharacterBridgeAdapter currentCharacterBridgeAdapter;

	@DisplayName("회원 번호를 받아 해당 회원의 현재 캐릭터의 이름을 반환한다")
	@Test
	void findCurrentCharacter() {
	    // given
		when(currentCharacterFinder.find(MEMBER_NO))
			.thenReturn(END_CURRENT_CHARACTER);

	    // when
		CharacterQuery actual = currentCharacterBridgeAdapter.findCurrentCharacter(MEMBER_NO);

		// then
		assertThat(actual).isNotNull();
		assertThat(actual.name()).isEqualTo(END_CURRENT_CHARACTER.getCharacterName());
	}
}
