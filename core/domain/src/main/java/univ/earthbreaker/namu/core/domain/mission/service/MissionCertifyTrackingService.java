package univ.earthbreaker.namu.core.domain.mission;

import org.springframework.stereotype.Service;

import univ.earthbreaker.namu.core.support.tx.TransactionHandler;

@Service
public class MissionCertifyTrackingService {

	// private final MissionCertifyStatusRepository missionCertifyStatusRepository;
	private final TransactionHandler transactionHandler;

	public MissionCertifyTrackingService(
		// MissionCertifyStatusRepository missionCertifyStatusRepository,
		TransactionHandler transactionHandler
	) {
		// this.missionCertifyStatusRepository = missionCertifyStatusRepository;
		this.transactionHandler = transactionHandler;
	}

	public void register(String requestId, long memberNo, long missionNo) {
		transactionHandler.execute(() -> {
			// missionCertifyStatusRepository.register(requestId, memberNo, missionNo);
			return null;
		});
	}

	public void update(String requestId, MissionCertifyProcess process) {
		transactionHandler.execute(() -> {
			// missionCertifyStatusRepository.update(requestId, process);
			return null;
		});
	}

	public MissionCertifyStatus retrieve(String requestId) {
		// return missionCertifyStatusRepository.retrieve(requestId);
		return null;
	}
}
