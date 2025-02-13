package univ.earthbreaker.namu.core.domain.mission;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static univ.earthbreaker.namu.core.domain.mission.MissionFixture.MEMBER_MISSIONS;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FixedMissionRetrieveStrategyTest {

	private final FixedMissionRetrieveStrategy fixedMissionRetrieveStrategy = new FixedMissionRetrieveStrategy();

	@DisplayName("특별한 날짜가 아닌 평번한 날짜에 실행되는 조회 전략으로, '특별 미션' 목록을 조회하지 않은 결과를 반환한다")
	@Test
	void retrieve() {
		// when
		MemberMissionQueryResult actual = fixedMissionRetrieveStrategy.retrieve(MEMBER_MISSIONS);

		// then
		assertAll(
			() -> assertThat(actual).isNotNull(),
			() -> assertThat(actual.todayMissions()).isNotNull().hasSize(1),
			() -> assertThat(actual.defaultMissions()).isNotNull().hasSize(1),
			() -> assertThat(actual.specialMissions()).isNotNull().isEmpty()
		);
	}
}
