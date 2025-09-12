package univ.earthbreaker.namu.core.domain.mission;

import java.util.List;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.mission.infra.MemberMissionRepository;

@Component
public class MemberMissionFinder {

	private final MemberMissionRepository memberMissionRepository;

	public MemberMissionFinder(MemberMissionRepository memberMissionRepository) {
		this.memberMissionRepository = memberMissionRepository;
	}

	public MemberMissions findAll(long memberNo) {
		List<MemberMission> memberMissions = memberMissionRepository.findAll(memberNo);
		return new MemberMissions(memberMissions);
	}

	public MemberMission find(long memberNo, long missionNo) {
		return memberMissionRepository.find(memberNo, missionNo);
	}
}
