package univ.earthbreaker.namu.core.domain.mission;

import org.springframework.stereotype.Component;

@Component
public class MemberMissionFinder {

	private final MemberMissionRepository memberMissionRepository;

	public MemberMissionFinder(MemberMissionRepository memberMissionRepository) {
		this.memberMissionRepository = memberMissionRepository;
	}

	MemberMission find(long memberNo) {
		return memberMissionRepository.find(memberNo);
	}
}
