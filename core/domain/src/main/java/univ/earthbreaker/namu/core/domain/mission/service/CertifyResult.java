package univ.earthbreaker.namu.core.domain.mission.service;

import univ.earthbreaker.namu.core.domain.mission.MissionCertifyProcess;

public record CertifyResult(
	String requestId,
	MissionCertifyProcess missionCertifyProcess
) {
	public boolean isPending() {
		return missionCertifyProcess.equals(MissionCertifyProcess.PENDING);
	}
}
