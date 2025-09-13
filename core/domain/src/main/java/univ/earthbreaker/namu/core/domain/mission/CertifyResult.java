package univ.earthbreaker.namu.core.service.mission;

import univ.earthbreaker.namu.core.domain.mission.MissionCertifyProcess;

public record CertifyResult(
	String requestId,
	MissionCertifyProcess missionCertifyProcess
) {
	public boolean isPending() {
		return missionCertifyProcess.equals(MissionCertifyProcess.PENDING);
	}
}
