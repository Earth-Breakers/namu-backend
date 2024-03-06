package univ.earthbreaker.namu.core.domain.character;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.BEGIN_STATUS;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.END_LEVEL_VALUE;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.END_STATUS;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.MIDDLE_LEVEL_VALUE;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.MIDDLE_STATUS;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CharacterStatusTest {

	@DisplayName("받은 경험치만큼 현재 경험치가 증가하고, 레벨업을 할 수 있는 상태가 되면 true 를, 그렇지 않다면 false 를 반환한다")
	@ParameterizedTest
	@CsvSource({"1, 1, false", "2, 2, false", "3, 3, true"})
	void addExp(int givenExp, int expectExp, boolean expectCanLevelUp) {
		// when
		CharacterStatus actual = BEGIN_STATUS.addExp(givenExp);

		// then
		assertAll(
			() -> assertThat(actual.getCurrentExp()).isEqualTo(expectExp),
			() -> assertThat(actual.isCanLevelUp()).isEqualTo(expectCanLevelUp)
		);
	}

	@DisplayName("캐릭터의 상태에 따라 다음 레벨의 기댓값을 계산해 반환한다")
	@Test
	void calculateToNextLevel() {
		// when
		int middleLevelExpect = BEGIN_STATUS.calculateToNextLevelValue();
		int endLevelExpect = MIDDLE_STATUS.calculateToNextLevelValue();
		int nextLevelExpect = END_STATUS.calculateToNextLevelValue();

		// then
		assertAll(
			() -> assertThat(middleLevelExpect).isEqualTo(MIDDLE_LEVEL_VALUE),
			() -> assertThat(endLevelExpect).isEqualTo(END_LEVEL_VALUE),
			() -> assertThat(nextLevelExpect).isEqualTo(END_LEVEL_VALUE + 1)
		);
	}

	@DisplayName("다음 기대 레벨값이 허용하는 레벨값을 초과했다면 true 를, 초과하지 않았다면 false 를 반환한다")
	@ParameterizedTest
	@CsvSource({"3, false", "4, true"})
	void isLevelOverFlow(int expectLevelValue, boolean expect) {
		// when
		boolean actual = BEGIN_STATUS.isExpectLevelOverFlow(expectLevelValue);

		// then
		assertThat(actual).isEqualTo(expect);
	}

	@DisplayName("캐릭터의 상태에 따라 현재 레벨이 MIDDLE 상태이면 true 를 반환하고, 그렇지 않다면 false 를 반환한다")
	@Test
	void isLevelMiddle() {
		// when, then
		assertAll(
			() -> assertThat(BEGIN_STATUS.isLevelMiddle()).isFalse(),
			() -> assertThat(MIDDLE_STATUS.isLevelMiddle()).isTrue(),
			() -> assertThat(END_STATUS.isLevelMiddle()).isFalse()
		);
	}

	@DisplayName("캐릭터의 상태에 따라 현재 레벨이 BEGIN 상태이면 true 를 반환하고, 그렇지 않다면 false 를 반환한다")
	@Test
	void isLevelBegin() {
		// when, then
		assertAll(
			() -> assertThat(BEGIN_STATUS.isLevelBegin()).isTrue(),
			() -> assertThat(MIDDLE_STATUS.isLevelBegin()).isFalse(),
			() -> assertThat(END_STATUS.isLevelBegin()).isFalse()
		);
	}
}
