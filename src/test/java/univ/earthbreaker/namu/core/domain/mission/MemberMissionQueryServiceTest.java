package univ.earthbreaker.namu.core.domain.mission;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static univ.earthbreaker.namu.core.domain.mission.MissionFixture.MEMBER_MISSION;
import static univ.earthbreaker.namu.core.domain.mission.MissionFixture.MEMBER_NO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberMissionQueryServiceTest {

	private @Mock MemberMissionFinder memberMissionFinder;
	private @Mock MissionRetrieveStrategyFactory missionRetrieveStrategyFactory;
	private @InjectMocks MemberMissionQueryService memberMissionQueryService;

	@DisplayName("회원 번호를 받아 회원의 미션 목록을 가져오고, 날짜에 맞는 조회 전략으로 미션들을 조회해 결과를 반환한다")
	@Test
	void retrieveMemberMissions() {
	    // given
		when(memberMissionFinder.find(MEMBER_NO))
			.thenReturn(MEMBER_MISSION);
		when(missionRetrieveStrategyFactory.get())
			.thenReturn(new FixedAndSpecialMissionRetrieveStrategy());

	    // when
		MemberMissionQueryResult result = memberMissionQueryService.retrieveMemberMissions(MEMBER_NO);

		// then
		assertAll(
			() -> assertThat(result.todayMissions()).isNotNull().hasSize(1),
			() -> assertThat(result.defaultMissions()).isNotNull().hasSize(1),
			() -> assertThat(result.specialMissions()).isNotNull().hasSize(1)
		);
	}
}
