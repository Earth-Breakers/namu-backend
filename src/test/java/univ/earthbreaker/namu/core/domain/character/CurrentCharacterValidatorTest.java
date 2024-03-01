package univ.earthbreaker.namu.core.domain.character;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.BEGIN_CURRENT_CHARACTER;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.BEGIN_CURRENT_CHARACTER_WITH_MAX_EXP;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.END_CURRENT_CHARACTER;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.END_CURRENT_CHARACTER_WITH_MAX_EXP;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.MIDDLE_CURRENT_CHARACTER;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.MIDDLE_CURRENT_CHARACTER_WITH_MAX_EXP;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CurrentCharacterValidatorTest {

	@DisplayName("현재 캐릭터의 상태가 레벨업이 가능하다면 아무런 예외도 발생하지 않는다")
	@Test
	void success_validateCanLevelUp() {
		// when, then
		assertThatNoException()
			.isThrownBy(() -> {
				CurrentCharacterValidator.validateCanLevelUp(BEGIN_CURRENT_CHARACTER_WITH_MAX_EXP);
				CurrentCharacterValidator.validateCanLevelUp(MIDDLE_CURRENT_CHARACTER_WITH_MAX_EXP);
			});
	}

	@DisplayName("현재 캐릭터의 상태가 레벨업이 불가능하다면 예외를 발생시킨다")
	@Test
	void fail_validateCanLevelUp() {
		// when, then
		assertThatThrownBy(() -> {
			CurrentCharacterValidator.validateCanLevelUp(BEGIN_CURRENT_CHARACTER);
			CurrentCharacterValidator.validateCanLevelUp(MIDDLE_CURRENT_CHARACTER);
			CurrentCharacterValidator.validateCanLevelUp(END_CURRENT_CHARACTER);
			CurrentCharacterValidator.validateCanLevelUp(END_CURRENT_CHARACTER_WITH_MAX_EXP);
		})
			.isInstanceOf(IllegalStateException.class)
			.hasMessage("현재 캐릭터는 아직 성장할 수 없는 상태입니다");
	}

	@DisplayName("현재 캐릭터의 레벨이 BEGIN 이면 아무런 예외도 발생하지 않는다")
	@Test
	void success_validateLevelIsBegin() {
		// when, then
		assertThatNoException()
			.isThrownBy(() -> {
				CurrentCharacterValidator.validateLevelIsBegin(BEGIN_CURRENT_CHARACTER);
				CurrentCharacterValidator.validateLevelIsBegin(BEGIN_CURRENT_CHARACTER_WITH_MAX_EXP);
			});
	}

	@DisplayName("현재 캐릭터의 레벨이 BEGIN 이 아니면 예외를 발생시킨다")
	@Test
	void fail_validateLevelIsBegin() {
		// when, then
		assertThatThrownBy(() -> {
			CurrentCharacterValidator.validateLevelIsBegin(MIDDLE_CURRENT_CHARACTER);
			CurrentCharacterValidator.validateLevelIsBegin(MIDDLE_CURRENT_CHARACTER_WITH_MAX_EXP);
			CurrentCharacterValidator.validateLevelIsBegin(END_CURRENT_CHARACTER);
			CurrentCharacterValidator.validateLevelIsBegin(END_CURRENT_CHARACTER_WITH_MAX_EXP);
		})
			.isInstanceOf(IllegalStateException.class)
			.hasMessage("레벨이 BEGIN 인 캐릭터만 growToRandom 메서드를 호출할 수 있습니다");
	}

	@DisplayName("현재 캐릭터의 레벨이 MIDDLE 이면 아무런 예외도 발생하지 않는다")
	@Test
	void success_validateLevelIsMiddle() {
		// when, then
		assertThatNoException()
			.isThrownBy(() -> {
				CurrentCharacterValidator.validateLevelIsMiddle(MIDDLE_CURRENT_CHARACTER);
				CurrentCharacterValidator.validateLevelIsMiddle(MIDDLE_CURRENT_CHARACTER_WITH_MAX_EXP);
			});
	}

	@DisplayName("현재 캐릭터의 레벨이 MIDDLE 이 아니면 예외를 발생시킨다")
	@Test
	void fail_validateLevelIsMiddle() {
		// when, then
		assertThatThrownBy(() -> {
			CurrentCharacterValidator.validateLevelIsMiddle(BEGIN_CURRENT_CHARACTER);
			CurrentCharacterValidator.validateLevelIsMiddle(BEGIN_CURRENT_CHARACTER_WITH_MAX_EXP);
			CurrentCharacterValidator.validateLevelIsMiddle(END_CURRENT_CHARACTER);
			CurrentCharacterValidator.validateLevelIsMiddle(END_CURRENT_CHARACTER_WITH_MAX_EXP);
		})
			.isInstanceOf(IllegalStateException.class)
			.hasMessage("레벨이 MIDDLE 인 캐릭터만 growToNext 메서드를 호출할 수 있습니다");
	}
}
