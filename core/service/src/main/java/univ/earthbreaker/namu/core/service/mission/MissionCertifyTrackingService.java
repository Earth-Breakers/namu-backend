package univ.earthbreaker.namu.core.service.mission;

import org.springframework.stereotype.Service;

import univ.earthbreaker.namu.core.domain.mission.MissionCertifyProcess;
import univ.earthbreaker.namu.core.domain.mission.MissionCertifyHistory;
import univ.earthbreaker.namu.core.domain.mission.infra.MissionCertifyHistoryRepository;
import univ.earthbreaker.namu.core.support.tx.TransactionHandler;

@Service
public class MissionCertifyTrackingService {

	private final MissionCertifyHistoryRepository missionCertifyHistoryRepository;
	private final TransactionHandler transactionHandler;

	public MissionCertifyTrackingService(
		MissionCertifyHistoryRepository missionCertifyHistoryRepository,
		TransactionHandler transactionHandler
	) {
		this.missionCertifyHistoryRepository = missionCertifyHistoryRepository;
		this.transactionHandler = transactionHandler;
	}

	public void register(String requestId, long memberNo, long missionNo) {
		transactionHandler.execute(() -> {
			missionCertifyHistoryRepository.register(requestId, memberNo, missionNo);
			return null;
		});
	}

	public void update(String requestId, MissionCertifyProcess process) {
		transactionHandler.execute(() -> {
			missionCertifyHistoryRepository.update(requestId, process);
			return null;
		});
	}

	public MissionCertifyHistory retrieve(String requestId) {
		return missionCertifyHistoryRepository.retrieve(requestId);
	}
}
