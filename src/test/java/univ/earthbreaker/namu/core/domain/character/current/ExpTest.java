package univ.earthbreaker.namu.core.domain.character.current;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import univ.earthbreaker.namu.core.domain.character.current.CurrentCharacterBadRequestException;
import univ.earthbreaker.namu.core.domain.character.current.Exp;

class ExpTest {

	@DisplayName("요구 경험치와 현재 경험치를 받아 Exp 를 생성할 수 있다 - 현재 경험치와 요구 경험치가 같으면 isFull 이 true 를 반환한다")
	@ParameterizedTest
	@CsvSource({"10, 9, false", "10, 10, true"})
	void of(int requiredExp, int currentExp, boolean isFullExpect) {
		// when
		Exp exp = Exp.of(requiredExp, currentExp);

		// then
		assertAll(
			() -> assertThat(exp.getRequiredExp()).isEqualTo(requiredExp),
			() -> assertThat(exp.getCurrentExp()).isEqualTo(currentExp),
			() -> assertThat(exp.isFull()).isEqualTo(isFullExpect)
		);
	}

	@DisplayName("요구 경험치를 받아 초기화 정적 팩토리 메서드를 통해 Exp 를 생성할 수 있다")
	@Test
	void initialize() {
		// when
		final int requiredExp = Integer.MAX_VALUE;
		Exp exp = Exp.initialize(requiredExp);

		// then
		assertAll(
			() -> assertThat(exp.getRequiredExp()).isEqualTo(requiredExp),
			() -> assertThat(exp.getCurrentExp()).isZero(),
			() -> assertThat(exp.isFull()).isFalse()
		);
	}

	@DisplayName("제공받은 경험치를 현재 경험치에 더하기 계산을 해 반환한다")
	@ParameterizedTest
	@CsvSource({"10, 9, false", "10, 10, true"})
	void success_calculate(int requiredExp, int givenExp, boolean isFullExpect) {
		// given
		Exp exp = Exp.initialize(requiredExp);

		// when
		Exp calculateAfter = exp.calculate(givenExp);

		// then
		assertAll(
			() -> assertThat(calculateAfter.getRequiredExp()).isEqualTo(requiredExp),
			() -> assertThat(calculateAfter.getCurrentExp()).isEqualTo(givenExp),
			() -> assertThat(calculateAfter.isFull()).isEqualTo(isFullExpect)
		);
	}

	@DisplayName("제공받은 경험치가 현재 경험치의 요구 경험치보다 크면 예외를 발생시킨다")
	@Test
	void fail_calculate() {
		// given
		final int requiredExp = 10;
		final int givenExp = requiredExp + 1;
		// when, then
		assertThatThrownBy(() -> Exp.initialize(requiredExp).calculate(givenExp))
			.isInstanceOf(CurrentCharacterBadRequestException.class)
			.hasMessage(CurrentCharacterBadRequestException.expOverflow().getMessage());
	}
}
