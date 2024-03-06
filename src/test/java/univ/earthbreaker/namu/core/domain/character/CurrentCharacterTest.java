package univ.earthbreaker.namu.core.domain.character;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.BEGIN_CURRENT_CHARACTER;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.BEGIN_CURRENT_CHARACTER_WITH_MAX_EXP;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.BEGIN_REQUIRED_EXP;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.END_CURRENT_CHARACTER;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.END_CURRENT_CHARACTER_WITH_MAX_EXP;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.END_LEVEL_VALUE;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.MIDDLE_CURRENT_CHARACTER;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.MIDDLE_CURRENT_CHARACTER_WITH_MAX_EXP;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.MIDDLE_LEVEL_VALUE;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.MIDDLE_REQUIRED_EXP;

import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CurrentCharacterTest {

	@DisplayName("""
		현재 캐릭터가 기본 타입(INITIAL)일때,
		다른 종류의 캐릭터 타입과 경험치를 제공하면
		1. 재공받은 캐릭터 타입으로 변경되고
		2. 제공받은 경험치 만큼 경험치가 증가한다""")
	@Test
	void success_giveInitialEnergyExp() {
		// given
		CharacterType giveCharacterType = CharacterType.BEAUTY;

		// when
		CurrentCharacter currentCharacter = BEGIN_CURRENT_CHARACTER.giveInitialEnergyExp(
			giveCharacterType,
			BEGIN_REQUIRED_EXP
		);

		// then
		assertAll(
			() -> assertThat(currentCharacter.getCharacterType()).isEqualTo(giveCharacterType),
			() -> assertThat(currentCharacter.getStatusCurrentExp()).isEqualTo(BEGIN_REQUIRED_EXP)
		);
	}

	@DisplayName("현재 캐릭터가 기본 타입(INITIAL)이 아닐때, 다른 종류의 캐릭터 타입을 제공하면 예외를 발생시킨다")
	@Test
	void fail_giveInitialEnergyExp() {
		// when, then
		assertThatThrownBy(() -> MIDDLE_CURRENT_CHARACTER.giveInitialEnergyExp(CharacterType.BEAUTY, BEGIN_REQUIRED_EXP))
			.isInstanceOf(CurrentCharacterBadRequestException.class)
			.hasMessage(CurrentCharacterBadRequestException.shouldBeInitial().getMessage());
	}

	@DisplayName("현재 캐릭터의 타입과 일치하는 캐릭터 타입과 경험치를 제공하면, 제공받은 경험치 만큼 경험치가 증가한다")
	@Test
	void success_giveEnergyExp() {
		// given
		CharacterType sameCharacterType = MIDDLE_CURRENT_CHARACTER.getCharacterType();

		// when
		CurrentCharacter currentCharacter = MIDDLE_CURRENT_CHARACTER.giveEnergyExp(
			sameCharacterType,
			BEGIN_REQUIRED_EXP
		);

		// then
		assertAll(
			() -> assertThat(currentCharacter.getCharacterType()).isEqualTo(sameCharacterType),
			() -> assertThat(currentCharacter.getStatusCurrentExp()).isEqualTo(BEGIN_REQUIRED_EXP)
		);
	}

	@DisplayName("현재 캐릭터의 타입과 일치하는 캐릭터 타입과 경험치를 제공하면, 제공받은 경험치 만큼 경험치가 증가한다")
	@Test
	void fail_giveEnergyExp() {
		// when, then
		CharacterType anotherCharacterType = CharacterType.PURIFY;
		assertThatThrownBy(() -> MIDDLE_CURRENT_CHARACTER.giveEnergyExp(anotherCharacterType, MIDDLE_REQUIRED_EXP))
			.isInstanceOf(CurrentCharacterBadRequestException.class)
			.hasMessage(CurrentCharacterBadRequestException.missMatch().getMessage());
	}

	@DisplayName("""
		다음 캐릭터(NamuCharacter)를 받아, 현재 캐릭터를 다음 캐릭터로 성장시킨다.
		- 다음 캐릭터의 속성이 현재 캐릭터의 속성으로 바뀐 현재 캐릭터를 반환한다
		- 바뀐 속성 : characterNo, level, requiredExp, currentExp, mainImagePath
		- 바뀌지 않은 속성 : memberNo, groupNumber, characterType""")
	@Test
	void success_growToNext() {
		// given
		CurrentCharacter beforeCharacter = BEGIN_CURRENT_CHARACTER;
		NamuCharacter nextNamuCharacter = NamuCharacter.builder()
			.no(Long.MAX_VALUE)
			.type(beforeCharacter.getCharacterType())
			.gender(Gender.MALE)
			.isEndangered(true)
			.groupNumber(beforeCharacter.getCharacterGroupNumber())
			.level(Level.MIDDLE.getValue())
			.requiredExp(MIDDLE_REQUIRED_EXP)
			.name("nextName")
			.description("nextDescription")
			.thumbnailImagePath("nextThumbnailImagePath")
			.mainImagePath("nextMainImagePath")
			.build();

		// when
		CurrentCharacter nextCharacter = beforeCharacter.growToNext(nextNamuCharacter);

		// then
		assertAll(
			() -> assertThat(nextCharacter.getTargetCharacterNo()).isEqualTo(nextNamuCharacter.getNo()),
			() -> assertThat(nextCharacter.getStatusLevel()).isEqualTo(nextNamuCharacter.getLevelValue()),
			() -> assertThat(nextCharacter.getStatusRequiredExp()).isEqualTo(nextNamuCharacter.getRequiredExp()),
			() -> assertThat(nextCharacter.getTargetCharacterMainImage()).isEqualTo(nextNamuCharacter.getMainImagePath()),
			() -> assertThat(nextCharacter.getStatusCurrentExp()).isZero(),
			() -> assertThat(nextCharacter.getMasterNo()).isEqualTo(beforeCharacter.getMasterNo()),
			() -> assertThat(nextCharacter.getCharacterGroupNumber()).isEqualTo(beforeCharacter.getCharacterGroupNumber()),
			() -> assertThat(nextCharacter.getCharacterType()).isEqualTo(beforeCharacter.getCharacterType())
		);
	}

	@DisplayName("다음 캐릭터(NamuCharacter)와 현재 캐릭터의 종류가 일치하지 않으면 예외를 발생시킨다")
	@Test
	void fail_growToNext() {
		// given
		CurrentCharacter currentCharacter = BEGIN_CURRENT_CHARACTER;
		NamuCharacter nextNamuCharacter = NamuCharacter.builder()
			.no(Long.MAX_VALUE)
			.type(CharacterType.BEAUTY)
			.gender(Gender.MALE)
			.isEndangered(true)
			.groupNumber(currentCharacter.getCharacterGroupNumber())
			.level(Level.MIDDLE.getValue())
			.requiredExp(MIDDLE_REQUIRED_EXP)
			.name("nextName")
			.description("nextDescription")
			.thumbnailImagePath("nextThumbnailImagePath")
			.mainImagePath("nextMainImagePath")
			.build();

		// when, then
		assertThatThrownBy(() -> currentCharacter.growToNext(nextNamuCharacter))
			.isInstanceOf(IllegalStateException.class)
			.hasMessage("캐릭터를 성장시키기 위해서는 캐릭터의 종류가 같아야 합니다");
	}

	@DisplayName("현재 캐릭터의 다음 레벨 기댓값을 계산해 반환한다")
	@Test
	void success_calculateExpectedNextLevel() {
		// given, when
		int middleLevelExpect = BEGIN_CURRENT_CHARACTER.calculateExpectedNextLevel();
		int endLevelExpect = MIDDLE_CURRENT_CHARACTER.calculateExpectedNextLevel();

		// then
		assertAll(
			() -> assertThat(middleLevelExpect).isEqualTo(MIDDLE_LEVEL_VALUE),
			() -> assertThat(endLevelExpect).isEqualTo(END_LEVEL_VALUE)
		);
	}

	@DisplayName("현재 캐릭터의 레벨이 END 일 때, 다음 레벨 기댓값을 계산하면 예외를 발생시킨다")
	@Test
	void fail_calculateExpectedNextLevel() {
		// when, then
		assertThatThrownBy(END_CURRENT_CHARACTER::calculateExpectedNextLevel)
			.isInstanceOf(IllegalStateException.class)
			.hasMessage("잘못된 요청으로 현재 혀용하는 level 의 최대치를 초과했습니다");
	}

	@DisplayName("현재 캐릭터의 레벨이 MIDDLE 이면 false 를, MIDDLE 이 아니라면 true 를 반환한다")
	@Test
	void levelIsNotMiddle() {
		// when
		boolean beginExpect = BEGIN_CURRENT_CHARACTER.levelIsNotMiddle();
		boolean middleExpect = MIDDLE_CURRENT_CHARACTER.levelIsNotMiddle();
		boolean endExpect = END_CURRENT_CHARACTER.levelIsNotMiddle();

		// then
		assertAll(
			() -> assertThat(beginExpect).isTrue(),
			() -> assertThat(middleExpect).isFalse(),
			() -> assertThat(endExpect).isTrue()
		);
	}

	@DisplayName("현재 캐릭터의 레벨이 BEGIN 이면 false 를, BEGIN 이 아니라면 true 를 반환한다")
	@Test
	void levelIsNotBegin() {
		// when
		boolean beginExpect = BEGIN_CURRENT_CHARACTER.levelIsNotBegin();
		boolean middleExpect = MIDDLE_CURRENT_CHARACTER.levelIsNotBegin();
		boolean endExpect = END_CURRENT_CHARACTER.levelIsNotBegin();

		// then
		assertAll(
			() -> assertThat(beginExpect).isFalse(),
			() -> assertThat(middleExpect).isTrue(),
			() -> assertThat(endExpect).isTrue()
		);
	}

	@DisplayName("현재 캐릭터를 받아 레벨업을 할 수 없는 상태이면 true 를, 레벨업을 할 수 있는 상태이면 false 를 반환한다")
	@ParameterizedTest
	@MethodSource("provideCurrentCharacterAndExpect")
	void cannotLevelUp(@NotNull CurrentCharacter currentCharacter, boolean expect) {
		// when
		boolean actual = currentCharacter.cannotLevelUp();

		// then
		assertThat(actual).isEqualTo(expect);
	}

	private static @NotNull Stream<Arguments> provideCurrentCharacterAndExpect() {
		return Stream.of(
			Arguments.of(BEGIN_CURRENT_CHARACTER, true),
			Arguments.of(BEGIN_CURRENT_CHARACTER_WITH_MAX_EXP, false),
			Arguments.of(MIDDLE_CURRENT_CHARACTER, true),
			Arguments.of(MIDDLE_CURRENT_CHARACTER_WITH_MAX_EXP, false),
			Arguments.of(END_CURRENT_CHARACTER, true),
			Arguments.of(END_CURRENT_CHARACTER_WITH_MAX_EXP, true)
		);
	}
}
