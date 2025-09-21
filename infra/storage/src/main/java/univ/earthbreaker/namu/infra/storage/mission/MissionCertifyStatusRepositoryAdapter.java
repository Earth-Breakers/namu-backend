package univ.earthbreaker.namu.infra.storage.mission;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.mission.MissionCertifyProcess;
import univ.earthbreaker.namu.core.domain.mission.MissionCertifyStatus;
import univ.earthbreaker.namu.core.domain.mission.infra.MissionCertifyStatusRepository;

@Component
public class MissionCertifyStatusRepositoryAdapter implements MissionCertifyStatusRepository {

	private final MissionCertifyHistoryJpaRepository jpaRepository;

	public MissionCertifyStatusRepositoryAdapter(MissionCertifyHistoryJpaRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	@Override
	public void register(String requestId, long memberNo, long missionNo) {
		jpaRepository.save(new MissionCertifyHistoryJpaEntity(
			requestId,
			memberNo,
			missionNo,
			MissionCertifyProcess.PENDING)
		);
	}

	@Override
	public void update(String requestId, MissionCertifyProcess process) {
		jpaRepository.updateHistory(requestId, process);
	}

	@Override
	public MissionCertifyStatus retrieve(String requestId) {
		return jpaRepository.findByRequestId(requestId).toDomainEntity();
	}
}
