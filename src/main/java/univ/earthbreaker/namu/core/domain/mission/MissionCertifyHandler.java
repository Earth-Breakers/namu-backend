package univ.earthbreaker.namu.core.domain.mission;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MissionCertifyHandler {

	private final MemberMissionRepository memberMissionRepository;

	public MissionCertifyHandler(MemberMissionRepository memberMissionRepository) {
		this.memberMissionRepository = memberMissionRepository;
	}

	@Transactional(propagation = Propagation.MANDATORY)
	public MemberMission success(@NotNull MemberMission memberMission) {
		MemberMission successMission = memberMission.success();
		memberMissionRepository.update(successMission);
		return successMission;
	}

	@Transactional
	public void failure(@NotNull MemberMission memberMission) {
		MemberMission successMission = memberMission.failure();
		memberMissionRepository.update(successMission);
	}
}
