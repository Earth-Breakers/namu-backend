package univ.earthbreaker.namu.core.domain.mission;

import static org.assertj.core.api.Assertions.assertThat;
import static univ.earthbreaker.namu.core.domain.mission.MissionFixture.BEACH_COMBING_DAY;
import static univ.earthbreaker.namu.core.domain.mission.MissionFixture.NORMAL_DAY;
import static univ.earthbreaker.namu.core.domain.mission.MissionFixture.TREE_PLANTING_DAY;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MissionRetrieveStrategyFactoryTest {

	private static final ZoneId SEOUL = ZoneId.of("Asia/Seoul");

	@DisplayName("""
		오늘의 날짜가
		'특별한 날'에 해당하면 FixedAndSpecialMissionRetrieveStrategy 을 반환하고
		특별한 날에 해당하지 않으면 FixedMissionRetrieveStrategy 을 반환한다""")
	@ParameterizedTest
	@MethodSource("provideDayAndExpectClassType")
	void get(LocalDate date, Class<MissionRetrieveStrategy> expect) {
		// given
		LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.MIDNIGHT);
		ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, SEOUL);
		Clock clock = Clock.fixed(zonedDateTime.toInstant(), SEOUL);
		MissionRetrieveStrategyFactory factory = new MissionRetrieveStrategyFactory(clock);

		// when
		MissionRetrieveStrategy actual = factory.get();

		// then
		assertThat(actual).isInstanceOf(expect);
	}

	private static @NotNull Stream<Arguments> provideDayAndExpectClassType() {
		return Stream.of(
			Arguments.of(TREE_PLANTING_DAY, FixedAndSpecialMissionRetrieveStrategy.class),
			Arguments.of(BEACH_COMBING_DAY, FixedAndSpecialMissionRetrieveStrategy.class),
			Arguments.of(NORMAL_DAY, FixedMissionRetrieveStrategy.class)
		);
	}
}
