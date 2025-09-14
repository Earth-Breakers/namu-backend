package univ.earthbreaker.namu.core.service.character;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import univ.earthbreaker.namu.core.domain.character.NamuCharacter;
import univ.earthbreaker.namu.core.domain.character.NamuCharacterNotFoundException;
import univ.earthbreaker.namu.core.domain.character.infra.CharacterRepository;

@ExtendWith(MockitoExtension.class)
class NamuCharacterFinderTest {

	private @Mock CharacterRepository characterRepository;
	private @InjectMocks NamuCharacterFinder namuCharacterFinder;

	@DisplayName("경험치가 가득 찬 MIDDLE 레벨의 현재 캐릭터에서 다음 END 레벨의 캐릭터(NamuCharacter)를 찾아와 반환한다")
	@Test
	void success_findNext() {
		// given
		when(characterRepository.findOrNull(any()))
			.thenReturn(CharacterFixture.END_NAMU_CHARACTER);

		// when
		NamuCharacter namuCharacter = namuCharacterFinder.findNext(
			3,
			CharacterFixture.MIDDLE_CURRENT_CHARACTER_WITH_MAX_EXP.getCharacterGroupNumber(),
			CharacterFixture.MIDDLE_CURRENT_CHARACTER_WITH_MAX_EXP.getCharacterType()
		);

		// then
		assertThat(namuCharacter).isEqualTo(CharacterFixture.END_NAMU_CHARACTER);
	}

	@DisplayName("다음 END 레벨의 캐릭터(NamuCharacter)를 찾지 못하면 예외를 발생시킨다")
	@Test
	void fail_findNext() {
		// given
		when(characterRepository.findOrNull(any()))
			.thenReturn(null);

		// when, then
		assertThatThrownBy(() -> namuCharacterFinder.findNext(
			3,
			CharacterFixture.MIDDLE_CURRENT_CHARACTER_WITH_MAX_EXP.getCharacterGroupNumber(),
			CharacterFixture.MIDDLE_CURRENT_CHARACTER_WITH_MAX_EXP.getCharacterType()
		))
			.isInstanceOf(NamuCharacterNotFoundException.class)
			.hasMessage(NamuCharacterNotFoundException.notFoundNext().getMessage());
	}

	@DisplayName("경험치가 가득 찬 BEGIN 레벨의 현재 캐릭터에서 다음 MIDDLE 레벨의 캐릭터(NamuCharacter)를 찾아와 반환한다")
	@Test
	void success_findRandom() {
		// given
		when(characterRepository.findRandomOrNull(any()))
			.thenReturn(CharacterFixture.MIDDLE_NAMU_CHARACTER);

		// when
		NamuCharacter namuCharacter = namuCharacterFinder.findRandom(
			CharacterFixture.BEGIN_CURRENT_CHARACTER_WITH_MAX_EXP.getStatusLevel(),
			CharacterFixture.BEGIN_CURRENT_CHARACTER_WITH_MAX_EXP.getCharacterGroupNumber(),
			true,
			CharacterFixture.BEGIN_CURRENT_CHARACTER_WITH_MAX_EXP.getCharacterType()
		);

		// then
		assertThat(namuCharacter).isEqualTo(CharacterFixture.MIDDLE_NAMU_CHARACTER);
	}

	@DisplayName("다음 MIDDLE 레벨의 캐릭터(NamuCharacter)를 찾지 못하면 예외를 발생시킨다")
	@Test
	void fail_findRandom() {
		// given
		when(characterRepository.findRandomOrNull(any()))
			.thenReturn(null);

		// when, then
		assertThatThrownBy(() -> namuCharacterFinder.findRandom(
			CharacterFixture.BEGIN_CURRENT_CHARACTER_WITH_MAX_EXP.getStatusLevel(),
			CharacterFixture.BEGIN_CURRENT_CHARACTER_WITH_MAX_EXP.getCharacterGroupNumber(),
			true,
			CharacterFixture.BEGIN_CURRENT_CHARACTER_WITH_MAX_EXP.getCharacterType()
		))
			.isInstanceOf(NamuCharacterNotFoundException.class)
			.hasMessage(NamuCharacterNotFoundException.notFoundRandom().getMessage());
	}
}
