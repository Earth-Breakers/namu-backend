package univ.earthbreaker.namu.core.service.mission;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import univ.earthbreaker.namu.core.domain.mission.MemberMission;
import univ.earthbreaker.namu.core.domain.mission.infra.MemberMissionRepository;

@Component
public class MissionCertifyHandler {

	private final MemberMissionRepository memberMissionRepository;

	public MissionCertifyHandler(MemberMissionRepository memberMissionRepository) {
		this.memberMissionRepository = memberMissionRepository;
	}

	@Transactional(propagation = Propagation.MANDATORY)
	public MemberMission success(MemberMission memberMission) {
		MemberMission successMission = memberMission.success();
		memberMissionRepository.update(successMission);
		return successMission;
	}

	@Transactional
	public void failure(MemberMission memberMission) {
		MemberMission successMission = memberMission.failure();
		memberMissionRepository.update(successMission);
	}
}
