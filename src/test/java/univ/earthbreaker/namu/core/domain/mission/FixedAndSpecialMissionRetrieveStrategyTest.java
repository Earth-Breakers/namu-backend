package univ.earthbreaker.namu.core.domain.mission;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FixedAndSpecialMissionRetrieveStrategyTest {

	private final FixedAndSpecialMissionRetrieveStrategy fixedAndSpecialMissionRetrieveStrategy
		= new FixedAndSpecialMissionRetrieveStrategy();

	@DisplayName("평번한 날짜가 아닌 특별한 날짜에 실행되는 조회 전략으로, '특별 미션' 목록을 포함해 조회한 결과를 반환한다")
	@Test
	void retrieve() {
		// given
		MemberMission memberMission = MissionFixture.MEMBER_MISSION;

		// when
		MemberMissionQueryResult actual = fixedAndSpecialMissionRetrieveStrategy.retrieve(memberMission);

		// then
		assertAll(
			() -> assertThat(actual).isNotNull(),
			() -> assertThat(actual.todayMissions()).isNotNull().hasSize(1),
			() -> assertThat(actual.defaultMissions()).isNotNull().hasSize(1),
			() -> assertThat(actual.specialMissions()).isNotNull().hasSize(1)
		);
	}
}
