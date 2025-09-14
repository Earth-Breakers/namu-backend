package univ.earthbreaker.namu.core.domain.mission;

public record CertifyResult(
	String requestId,
	MissionCertifyProcess missionCertifyProcess
) {
	public boolean isPending() {
		return missionCertifyProcess.equals(MissionCertifyProcess.PENDING);
	}
}
