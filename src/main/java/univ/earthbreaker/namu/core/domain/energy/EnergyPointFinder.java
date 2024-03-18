package univ.earthbreaker.namu.core.domain.energy;

import org.springframework.stereotype.Component;

@Component
public class EnergyPointFinder {

	private final EnergyPointRepository energyPointRepository;

	EnergyPointFinder(EnergyPointRepository energyPointRepository) {
		this.energyPointRepository = energyPointRepository;
	}

	Energy find(long memberNo) {
		return energyPointRepository.find(memberNo);
	}
}
