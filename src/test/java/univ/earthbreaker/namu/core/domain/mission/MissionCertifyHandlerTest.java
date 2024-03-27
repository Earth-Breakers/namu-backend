package univ.earthbreaker.namu.core.domain.mission;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static univ.earthbreaker.namu.core.domain.mission.MissionFixture.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MissionCertifyHandlerTest {

	private @Mock MemberMissionRepository memberMissionRepository;
	private @InjectMocks MissionCertifyHandler missionCertifyHandler;

	@DisplayName("회원의 인증 완료된 미션을 성공 상태로 변경해 갱신한 뒤, 갱신된 값을 반환한다")
	@Test
	void success() {
	    // given
		MemberMission expect = DEFAULT_MISSION_READY.success();

		// when
		MemberMission actual = missionCertifyHandler.success(DEFAULT_MISSION_READY);

		// then
		assertThat(actual).isNotNull().isEqualTo(expect);
		verify(memberMissionRepository).update(expect);
	}

	@DisplayName("회원의 인증 완료된 미션을 실패 상태로 변경해 갱신한다")
	@Test
	void failure() {
		// given
		MemberMission expectFailureMission = DEFAULT_MISSION_READY.failure();

		// when
		missionCertifyHandler.failure(DEFAULT_MISSION_READY);

		// then
		verify(memberMissionRepository).update(expectFailureMission);
	}
}
