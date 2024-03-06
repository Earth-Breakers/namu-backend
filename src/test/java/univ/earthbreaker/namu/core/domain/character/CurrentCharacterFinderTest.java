package univ.earthbreaker.namu.core.domain.character;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.BEGIN_CURRENT_CHARACTER;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CurrentCharacterFinderTest {

	private static final long MEMBER_NO = 1L;

	private @Mock CurrentCharacterRepository currentCharacterRepository;
	private @InjectMocks CurrentCharacterFinder currentCharacterFinder;

	@DisplayName("회원 번호를 받아, 회원 번호에 해당하는 현재 캐릭터를 반환한다")
	@Test
	void success_find() {
	    // given
		when(currentCharacterRepository.findOrNull(MEMBER_NO))
			.thenReturn(BEGIN_CURRENT_CHARACTER);

	    // when
		CurrentCharacter currentCharacter = currentCharacterFinder.find(MEMBER_NO);

		// then
		assertAll(
			() -> assertThat(currentCharacter.getMasterNo()).isEqualTo(MEMBER_NO),
			() -> assertThat(currentCharacter.getTargetCharacterNo()).isEqualTo(BEGIN_CURRENT_CHARACTER.getTargetCharacterNo()),
			() -> assertThat(currentCharacter.getCharacterType()).isEqualTo(BEGIN_CURRENT_CHARACTER.getCharacterType()),
			() -> assertThat(currentCharacter.getCharacterGroupNumber()).isEqualTo(BEGIN_CURRENT_CHARACTER.getCharacterGroupNumber()),
			() -> assertThat(currentCharacter.getTargetCharacterMainImage()).isEqualTo(BEGIN_CURRENT_CHARACTER.getTargetCharacterMainImage()),
			() -> assertThat(currentCharacter.getStatusLevel()).isEqualTo(BEGIN_CURRENT_CHARACTER.getStatusLevel()),
			() -> assertThat(currentCharacter.getStatusRequiredExp()).isEqualTo(BEGIN_CURRENT_CHARACTER.getStatusRequiredExp()),
			() -> assertThat(currentCharacter.getStatusCurrentExp()).isEqualTo(BEGIN_CURRENT_CHARACTER.getStatusCurrentExp())
		);
	}

	@DisplayName("회원 번호를 받아, 회원 번호에 해당하는 현재 캐릭터가 없다면 예외를 발생시킨다")
	@Test
	void fail_find() {
		// given
		when(currentCharacterRepository.findOrNull(MEMBER_NO))
			.thenReturn(null);

		// when, then
		assertThatThrownBy(() -> currentCharacterFinder.find(MEMBER_NO))
			.isInstanceOf(CurrentCharacterNotFoundException.class)
			.hasMessage(CurrentCharacterNotFoundException.notFound(MEMBER_NO).getMessage());
	}
}
