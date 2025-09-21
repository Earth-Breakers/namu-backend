package univ.earthbreaker.namu.core.domain.mission.infra;

import univ.earthbreaker.namu.core.domain.mission.MissionCertifyProcess;
import univ.earthbreaker.namu.core.domain.mission.MissionCertifyStatus;

public interface MissionCertifyStatusRepository {

	void register(String requestId, long memberNo, long missionNo);

	void update(String requestId, MissionCertifyProcess process);

	MissionCertifyStatus retrieve(String requestId);
}
