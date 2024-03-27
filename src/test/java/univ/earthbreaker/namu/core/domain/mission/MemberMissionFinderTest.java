package univ.earthbreaker.namu.core.domain.mission;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static univ.earthbreaker.namu.core.domain.mission.MissionFixture.MEMBER_NO;
import static univ.earthbreaker.namu.core.domain.mission.MissionFixture.MISSIONS;

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
	void findAll() {
	    // given
		when(memberMissionRepository.findAll(MEMBER_NO))
			.thenReturn(MISSIONS);

	    // when
		MemberMissions actual = memberMissionFinder.findAll(MEMBER_NO);

		// then
		assertThat(actual).isNotNull();
	}
}
