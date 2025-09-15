package univ.earthbreaker.namu.core.service.character.current;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import univ.earthbreaker.namu.core.domain.character.current.CurrentCharacter;
import univ.earthbreaker.namu.core.service.character.CharacterFixture;

class CurrentCharacterValidatorTest {

	@DisplayName("현재 캐릭터의 상태가 레벨업이 가능하다면 아무런 예외도 발생하지 않는다")
	@Test
	void success_validateCanLevelUp() {
		// when, then
		assertThatNoException()
			.isThrownBy(() -> {
				CurrentCharacterValidator.validateCanLevelUp(CharacterFixture.BEGIN_CURRENT_CHARACTER_WITH_MAX_EXP);
				CurrentCharacterValidator.validateCanLevelUp(CharacterFixture.MIDDLE_CURRENT_CHARACTER_WITH_MAX_EXP);
				CurrentCharacterValidator.validateCanLevelUp(CharacterFixture.END_CURRENT_CHARACTER_WITH_MAX_EXP);
			});
	}

	@DisplayName("현재 캐릭터의 상태가 레벨업이 불가능하다면 예외를 발생시킨다")
	@ParameterizedTest
	@MethodSource("provideCannotLevelUpCurrentCharacter")
	void fail_validateCanLevelUp(CurrentCharacter currentCharacter) {
		// when, then
		assertThatThrownBy(() -> CurrentCharacterValidator.validateCanLevelUp(currentCharacter))
			.isInstanceOf(IllegalStateException.class)
			.hasMessage("현재 캐릭터는 아직 성장할 수 없는 상태입니다");
	}

	@DisplayName("현재 캐릭터의 레벨이 BEGIN 이면 아무런 예외도 발생하지 않는다")
	@Test
	void success_validateLevelIsBegin() {
		// when, then
		assertThatNoException()
			.isThrownBy(() -> {
				CurrentCharacterValidator.validateLevelIsBegin(CharacterFixture.BEGIN_CURRENT_CHARACTER);
				CurrentCharacterValidator.validateLevelIsBegin(CharacterFixture.BEGIN_CURRENT_CHARACTER_WITH_MAX_EXP);
			});
	}

	@DisplayName("현재 캐릭터의 레벨이 BEGIN 이 아니면 예외를 발생시킨다")
	@ParameterizedTest
	@MethodSource("provideNotBeginCurrentCharacter")
	void fail_validateLevelIsBegin(CurrentCharacter currentCharacter) {
		// when, then
		assertThatThrownBy(() -> CurrentCharacterValidator.validateLevelIsBegin(currentCharacter))
			.isInstanceOf(IllegalStateException.class)
			.hasMessage("레벨이 BEGIN 인 캐릭터만 growToRandom 메서드를 호출할 수 있습니다");
	}

	@DisplayName("현재 캐릭터의 레벨이 MIDDLE 이면 아무런 예외도 발생하지 않는다")
	@Test
	void success_validateLevelIsMiddle() {
		// when, then
		assertThatNoException()
			.isThrownBy(() -> {
				CurrentCharacterValidator.validateLevelIsMiddle(CharacterFixture.MIDDLE_CURRENT_CHARACTER);
				CurrentCharacterValidator.validateLevelIsMiddle(CharacterFixture.MIDDLE_CURRENT_CHARACTER_WITH_MAX_EXP);
			});
	}

	@DisplayName("현재 캐릭터의 레벨이 MIDDLE 이 아니면 예외를 발생시킨다")
	@ParameterizedTest
	@MethodSource("provideNotMiddleCurrentCharacter")
	void fail_validateLevelIsMiddle(CurrentCharacter currentCharacter) {
		// when, then
		assertThatThrownBy(() -> CurrentCharacterValidator.validateLevelIsMiddle(currentCharacter))
			.isInstanceOf(IllegalStateException.class)
			.hasMessage("레벨이 MIDDLE 인 캐릭터만 growToNext 메서드를 호출할 수 있습니다");
	}

	@DisplayName("현재 캐릭터의 레벨이 END 이면 아무런 예외도 발생하지 않는다")
	@Test
	void success_validateLevelIsEnd() {
		// when, then
		assertThatNoException()
			.isThrownBy(() -> {
				CurrentCharacterValidator.validateLevelIsEnd(CharacterFixture.END_CURRENT_CHARACTER);
				CurrentCharacterValidator.validateLevelIsEnd(CharacterFixture.END_CURRENT_CHARACTER_WITH_MAX_EXP);
			});
	}

	@DisplayName("현재 캐릭터의 레벨이 END 이 아니면 예외를 발생시킨다")
	@ParameterizedTest
	@MethodSource("provideNotEndCurrentCharacter")
	void fail_validateLevelIsEnd(CurrentCharacter currentCharacter) {
		// when, then
		assertThatThrownBy(() -> CurrentCharacterValidator.validateLevelIsEnd(currentCharacter))
			.isInstanceOf(IllegalStateException.class)
			.hasMessage("레벨이 END 인 캐릭터만 growToFinal 메서드를 호출할 수 있습니다");
	}

	@DisplayName("현재 캐릭터의 레벨이 FINAL 이면 아무런 예외도 발생하지 않는다")
	@Test
	void success_validateLevelIsFinal() {
		// when, then
		assertThatNoException()
			.isThrownBy(() -> CurrentCharacterValidator.validateLevelIsFinal(CharacterFixture.FINAL_CURRENT_CHARACTER));
	}

	@DisplayName("현재 캐릭터의 레벨이 FINAL 이 아니면 예외를 발생시킨다")
	@ParameterizedTest
	@MethodSource("provideNotFinalCurrentCharacter")
	void fail_validateLevelIsFinal(CurrentCharacter currentCharacter) {
		// when, then
		assertThatThrownBy(() -> CurrentCharacterValidator.validateLevelIsFinal(currentCharacter))
			.isInstanceOf(IllegalStateException.class)
			.hasMessage("레벨이 FINAL 인 캐릭터만 initialize 메서드를 호출할 수 있습니다");
	}

	private static Stream<Arguments> provideCannotLevelUpCurrentCharacter() {
		return Stream.of(
			Arguments.of(CharacterFixture.BEGIN_CURRENT_CHARACTER),
			Arguments.of(CharacterFixture.MIDDLE_CURRENT_CHARACTER),
			Arguments.of(CharacterFixture.END_CURRENT_CHARACTER),
			Arguments.of(CharacterFixture.FINAL_CURRENT_CHARACTER)
		);
	}

	private static Stream<Arguments> provideNotBeginCurrentCharacter() {
		return Stream.of(
			Arguments.of(CharacterFixture.MIDDLE_CURRENT_CHARACTER),
			Arguments.of(CharacterFixture.MIDDLE_CURRENT_CHARACTER_WITH_MAX_EXP),
			Arguments.of(CharacterFixture.END_CURRENT_CHARACTER),
			Arguments.of(CharacterFixture.END_CURRENT_CHARACTER_WITH_MAX_EXP),
			Arguments.of(CharacterFixture.FINAL_CURRENT_CHARACTER)
		);
	}

	private static Stream<Arguments> provideNotMiddleCurrentCharacter() {
		return Stream.of(
			Arguments.of(CharacterFixture.BEGIN_CURRENT_CHARACTER),
			Arguments.of(CharacterFixture.BEGIN_CURRENT_CHARACTER_WITH_MAX_EXP),
			Arguments.of(CharacterFixture.END_CURRENT_CHARACTER),
			Arguments.of(CharacterFixture.END_CURRENT_CHARACTER_WITH_MAX_EXP),
			Arguments.of(CharacterFixture.FINAL_CURRENT_CHARACTER)
		);
	}

	private static Stream<Arguments> provideNotEndCurrentCharacter() {
		return Stream.of(
			Arguments.of(CharacterFixture.BEGIN_CURRENT_CHARACTER),
			Arguments.of(CharacterFixture.BEGIN_CURRENT_CHARACTER_WITH_MAX_EXP),
			Arguments.of(CharacterFixture.MIDDLE_CURRENT_CHARACTER),
			Arguments.of(CharacterFixture.MIDDLE_CURRENT_CHARACTER_WITH_MAX_EXP),
			Arguments.of(CharacterFixture.FINAL_CURRENT_CHARACTER)
		);
	}

	private static Stream<Arguments> provideNotFinalCurrentCharacter() {
		return Stream.of(
			Arguments.of(CharacterFixture.BEGIN_CURRENT_CHARACTER),
			Arguments.of(CharacterFixture.BEGIN_CURRENT_CHARACTER_WITH_MAX_EXP),
			Arguments.of(CharacterFixture.MIDDLE_CURRENT_CHARACTER),
			Arguments.of(CharacterFixture.MIDDLE_CURRENT_CHARACTER_WITH_MAX_EXP)
		);
	}
}
