package univ.earthbreaker.namu.core.domain.mission;

public class MissionCertifyStatus {

	private final String requestId;
	private final long memberNo;
	private final long missionNo;
	private final MissionCertifyProcess process;

	public MissionCertifyStatus(String requestId, long memberNo, long missionNo, MissionCertifyProcess process) {
		this.requestId = requestId;
		this.memberNo = memberNo;
		this.missionNo = missionNo;
		this.process = process;
	}

	public String getRequestId() {
		return requestId;
	}

	public long getMemberNo() {
		return memberNo;
	}

	public long getMissionNo() {
		return missionNo;
	}

	public MissionCertifyProcess getProcess() {
		return process;
	}
}
