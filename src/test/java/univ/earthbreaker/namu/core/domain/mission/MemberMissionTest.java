package univ.earthbreaker.namu.core.domain.mission;

import static org.assertj.core.api.Assertions.assertThat;
import static univ.earthbreaker.namu.core.domain.mission.MissionFixture.MEMBER_MISSION;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberMissionTest {

	@DisplayName("회원의 미션 목록에서 '오늘의 미션' 목록을 반환한다")
	@Test
	void findTodayMissionList() {
		// when
		List<Mission> actual = MEMBER_MISSION.findTodayMissionList();

		// then
		assertThat(actual)
			.isNotNull()
			.hasSize(1);
	}

	@DisplayName("회원의 미션 목록에서 '기본 미션' 목록을 반환한다")
	@Test
	void findDefaultMissionList() {
		// when
		List<Mission> actual = MEMBER_MISSION.findDefaultMissionList();

		// then
		assertThat(actual)
			.isNotNull()
			.hasSize(1);
	}

	@DisplayName("회원의 미션 목록에서 '특별 미션' 목록을 반환한다")
	@Test
	void findSpecialMissionList() {
		// when
		List<Mission> actual = MEMBER_MISSION.findSpecialMissionList();

		// then
		assertThat(actual)
			.isNotNull()
			.hasSize(1);
	}
}
