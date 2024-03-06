package univ.earthbreaker.namu.core.domain.character;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class LevelTest {

	@DisplayName("레벨값을 받아 해당 레벨 객체를 생성할 수 있다")
	@ParameterizedTest
	@MethodSource("provideLevelValueAndExpectLevel")
	void success_of(int levelValue, Level expectLevel, boolean isGrowableExpect) {
		// when
		Level actualLevel = Level.of(levelValue);

		// then
		assertAll(
			() -> assertThat(actualLevel).isEqualTo(expectLevel),
			() -> assertThat(actualLevel.isGrowable()).isEqualTo(isGrowableExpect)
		);
	}

	private static @NotNull Stream<Arguments> provideLevelValueAndExpectLevel() {
		return Stream.of(
			Arguments.of(1, Level.BEGIN, true),
			Arguments.of(2, Level.MIDDLE, true),
			Arguments.of(3, Level.END, false)
		);
	}

	@DisplayName("END 레벨값 이상의 값이 들어오면 예외를 발생시킨다")
	@Test
	void fail_of() {
		// given
		int overflowLevelValue = Level.END.up();

		// when, then
		assertThatThrownBy(() -> Level.of(overflowLevelValue))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("허용하지 않는 level 값입니다");
	}

	@DisplayName("Level 의 종류에 상관없이, 받은 레벨값이 허용하는 최대 레벨값보다 크면 true 를, 작거나 같으면 false 를 반환한다")
	@ParameterizedTest
	@CsvSource({"4, 3"})
	void isOverflow(int overflowLevelValue, int boundaryLevelValue) {
		// given
		Level begin = Level.BEGIN;
		Level middle = Level.MIDDLE;
		Level end = Level.END;

		// when, then
		assertAll(
			() -> assertThat(begin.isOverflow(overflowLevelValue)).isTrue(),
			() -> assertThat(middle.isOverflow(overflowLevelValue)).isTrue(),
			() -> assertThat(end.isOverflow(overflowLevelValue)).isTrue(),
			() -> assertThat(begin.isOverflow(boundaryLevelValue)).isFalse(),
			() -> assertThat(middle.isOverflow(boundaryLevelValue)).isFalse(),
			() -> assertThat(end.isOverflow(boundaryLevelValue)).isFalse()
		);
	}
}
