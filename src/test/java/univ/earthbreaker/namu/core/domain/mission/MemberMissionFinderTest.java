package univ.earthbreaker.namu.core.domain.mission;

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
class MemberMissionFinderTest {

	private @Mock MemberMissionRepository memberMissionRepository;
	private @InjectMocks MemberMissionFinder memberMissionFinder;

	@DisplayName("회원의 번호를 받아 회원에게 할당된 미션 목록을 반환한다")
	@Test
	void find() {
	    // given
		when(memberMissionRepository.find(MEMBER_NO))
			.thenReturn(MEMBER_MISSION);

	    // when
		MemberMission actual = memberMissionFinder.find(MEMBER_NO);

		// then
		assertThat(actual).isEqualTo(MEMBER_MISSION);
	}
}
