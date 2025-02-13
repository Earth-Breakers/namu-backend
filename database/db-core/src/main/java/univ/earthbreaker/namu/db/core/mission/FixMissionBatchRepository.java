package univ.earthbreaker.namu.db.core.mission;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class FixMissionBatchRepository {

	private final FixMissionJpaRepository fixMissionJpaRepository;

	public FixMissionBatchRepository(FixMissionJpaRepository fixMissionJpaRepository) {
		this.fixMissionJpaRepository = fixMissionJpaRepository;
	}

	public List<FixMissionJpaEntity> findDefaultMissionsByRandom() {
		return fixMissionJpaRepository.findDefaultMissionsByRandom();
	}

	public List<FixMissionJpaEntity> findTodayMissionsByRandom() {
		return fixMissionJpaRepository.findTodayMissionsByRandom();
	}

	public List<FixMissionJpaEntity> findSpecialMissions() {
		return fixMissionJpaRepository.findSpecialMissions();
	}
}
