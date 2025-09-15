package univ.earthbreaker.namu.core.service.mission;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import univ.earthbreaker.namu.core.domain.mission.MemberMissions;
import univ.earthbreaker.namu.core.domain.mission.infra.MemberMissionRepository;

@ExtendWith(MockitoExtension.class)
class MemberMissionFinderTest {

	private @Mock MemberMissionRepository memberMissionRepository;
	private @InjectMocks MemberMissionFinder memberMissionFinder;

	@DisplayName("회원의 번호를 받아 회원에게 할당된 미션 목록을 반환한다")
	@Test
	void findAll() {
	    // given
		when(memberMissionRepository.findAll(MissionFixture.MEMBER_NO))
			.thenReturn(MissionFixture.MISSIONS);

	    // when
		MemberMissions actual = memberMissionFinder.findAll(MissionFixture.MEMBER_NO);

		// then
		assertThat(actual).isNotNull();
	}
}
