package univ.earthbreaker.namu.core.domain.mission.infra;

import univ.earthbreaker.namu.core.domain.mission.MissionCertifyProcess;
import univ.earthbreaker.namu.core.domain.mission.MissionCertifyHistory;

public interface MissionCertifyHistoryRepository {

	void register(String requestId, long memberNo, long missionNo);

	void update(String requestId, MissionCertifyProcess process);

	MissionCertifyHistory retrieve(String requestId);
}
