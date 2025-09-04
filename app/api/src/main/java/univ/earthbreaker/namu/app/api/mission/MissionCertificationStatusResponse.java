package univ.earthbreaker.namu.app.api.mission;

import univ.earthbreaker.namu.core.domain.mission.MissionCertifyStatus;

public record MissionCertificationStatusResponse(
	String requestId,
	long memberNo,
	long missionNo,
	String process
) {
	static MissionCertificationStatusResponse from(MissionCertifyStatus status) {
		return new MissionCertificationStatusResponse(
			status.getRequestId(),
			status.getMemberNo(),
			status.getMissionNo(),
			status.getProcess().name()
		);
	}
}
