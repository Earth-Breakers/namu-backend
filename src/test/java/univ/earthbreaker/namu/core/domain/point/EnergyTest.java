package univ.earthbreaker.namu.core.domain.point;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static univ.earthbreaker.namu.core.domain.point.EnergyPointFixture.EMPTY_ENERGY;
import static univ.earthbreaker.namu.core.domain.point.EnergyPointFixture.ENERGY;
import static univ.earthbreaker.namu.core.domain.point.EnergyPointFixture.USE_OVER_POINT_VALUE;
import static univ.earthbreaker.namu.core.domain.point.EnergyPointFixture.USE_POINT_VALUE;

import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class EnergyTest {

	@DisplayName("사용할 에너지 포인트 값을 받아 해당 포인트 만큼 사용하고, 남은 에너지를 반환한다")
	@Test
	void success_use() {
	    // given
		Energy energy = ENERGY;

		// when
		Energy actual = energy.use(USE_POINT_VALUE);

		// then
		assertThat(actual).isNotNull();
		assertThatNoException()
			.isThrownBy(() -> energy.use(USE_POINT_VALUE));
	}

	@DisplayName("""
		사용할 에너지 포인트 값을 받아, 사용할 수 있는 최대 허용 포인트를 초과하거나
		사용할 수 있는 포인트가 없다면 예외를 발생시킨다""")
	@ParameterizedTest
	@MethodSource("provideEnergyAndUsePointAndExpectExceptionMessage")
	void fail_use(Energy energy, int usePointValue, String expectExceptionMessage) {
		// when, then
		assertThatThrownBy(() -> energy.use(usePointValue))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage(expectExceptionMessage);
	}

	private static @NotNull Stream<Arguments> provideEnergyAndUsePointAndExpectExceptionMessage() {
		return Stream.of(
			Arguments.of(ENERGY, USE_OVER_POINT_VALUE, "사용할 수 있는 최대 허용 포인트를 초과했습니다"),
			Arguments.of(EMPTY_ENERGY, USE_POINT_VALUE, "사용할 수 있는 포인트가 없습니다")
		);
	}

	@DisplayName("에너지 포인트 값을 받아 해당 포인트 만큼 추가하고, 추가한 에너지를 반환한다")
	@Test
	void receive() {
		// when
		Energy actual = ENERGY.receive(USE_POINT_VALUE);

		// then
		assertThat(actual).isNotNull();
	}
}
