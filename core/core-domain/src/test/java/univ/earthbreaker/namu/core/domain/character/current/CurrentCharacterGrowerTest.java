package univ.earthbreaker.namu.core.domain.character.current;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import univ.earthbreaker.namu.core.domain.character.CharacterType;
import univ.earthbreaker.namu.core.domain.character.NamuCharacterFinder;

@ExtendWith(MockitoExtension.class)
class CurrentCharacterGrowerTest {

	private @Mock NamuCharacterFinder namuCharacterFinder;
	private @Mock CurrentCharacterFinder currentCharacterFinder;
	private @Mock CurrentCharacterRepository currentCharacterRepository;
	private @Mock EndangeredProbabilityPolicy endangeredProbabilityPolicy;
	private @InjectMocks CurrentCharacterGrower currentCharacterGrower;

	@DisplayName("""
		회원 번호를 받아, 현재 캐릭터를 같은 타입의 다음 레벨 캐릭터로 성장시킬 수 있다
		- 현재 캐릭터 LEVEL 을 BEGIN -> MIDDLE 로 성장시킨다""")
	@Test
	void growToMiddle() {
		// given
		when(currentCharacterFinder.find(MEMBER_NO))
			.thenReturn(BEGIN_CURRENT_CHARACTER_WITH_MAX_EXP);
		when(namuCharacterFinder.findNext(anyInt(), anyInt(), any(CharacterType.class)))
			.thenReturn(MIDDLE_NAMU_CHARACTER);

		// when
		currentCharacterGrower.growToMiddle(MEMBER_NO);

		// then
		verify(currentCharacterRepository).update(any(CurrentCharacter.class));
	}

	@DisplayName("""
		회원 번호를 받아, 현재 캐릭터를 같은 타입의 다음 레벨 캐릭터로 성장시킬 수 있다
		- 현재 캐릭터 LEVEL 을 MIDDLE -> END 로 성장시킨다""")
	@Test
	void growToEnd() {
		// given
		when(currentCharacterFinder.find(MEMBER_NO))
			.thenReturn(MIDDLE_CURRENT_CHARACTER_WITH_MAX_EXP);
		when(namuCharacterFinder.findNext(anyInt(), anyInt(), any(CharacterType.class)))
			.thenReturn(END_NAMU_CHARACTER);

		// when
		currentCharacterGrower.growToEnd(MEMBER_NO);

		// then
		verify(currentCharacterRepository).update(any(CurrentCharacter.class));
	}

	@DisplayName("""
		회원 번호를 받아, 현재 캐릭터를 같은 타입인 랜덤의 최종 진화 캐릭터로 성장시킬 수 있다
		- 현재 캐릭터 LEVEL 을 END -> FINAL 로 성장시키고, LEVEL.FINAL 로 최종 진화한 캐릭터를 반환한다""")
	@Test
	void growToFinal() {
		// given
		when(currentCharacterFinder.find(MEMBER_NO))
			.thenReturn(END_CURRENT_CHARACTER_WITH_MAX_EXP);
		when(namuCharacterFinder.findRandom(anyInt(), anyInt(), anyBoolean(), any(CharacterType.class)))
			.thenReturn(FINAL_NAMU_CHARACTER);

		// when
		CurrentCharacter actual = currentCharacterGrower.growToFinal(MEMBER_NO);

		// then
		assertAll(
			() -> assertThat(actual).isNotNull(),
			() -> verify(endangeredProbabilityPolicy).determineEndangered(),
			() -> verify(currentCharacterRepository).update(any(CurrentCharacter.class))
		);
	}

	@Deprecated
	@DisplayName("""
		회원 번호를 받아, 현재 캐릭터를 같은 타입인 랜덤의 다음 레벨 캐릭터로 성장시킬 수 있다
		- 현재 캐릭터 LEVEL 을 BEGIN -> MIDDLE 로 성장시킨다""")
	@Test
	void growToRandom() {
		// given
		when(currentCharacterFinder.find(MEMBER_NO))
			.thenReturn(BEGIN_CURRENT_CHARACTER_WITH_MAX_EXP);
		when(namuCharacterFinder.findRandom(anyInt(), anyInt(), anyBoolean(), any(CharacterType.class)))
			.thenReturn(MIDDLE_NAMU_CHARACTER);

		// when
		currentCharacterGrower.growToRandom(MEMBER_NO);

		// then
		assertAll(
			() -> verify(endangeredProbabilityPolicy).determineEndangered(),
			() -> verify(currentCharacterRepository).update(any(CurrentCharacter.class))
		);
	}
}
