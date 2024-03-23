package univ.earthbreaker.namu.core.domain.mission;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MemberMissionFinder {

	private final MemberMissionRepository memberMissionRepository;

	public MemberMissionFinder(MemberMissionRepository memberMissionRepository) {
		this.memberMissionRepository = memberMissionRepository;
	}

	MemberMissions findAll(long memberNo) {
		List<MemberMission> memberMissions = memberMissionRepository.findAll(memberNo);
		return new MemberMissions(memberMissions);
	}
}
