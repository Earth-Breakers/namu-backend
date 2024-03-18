package univ.earthbreaker.namu.core.domain.energy;

import org.springframework.stereotype.Component;

@Component
public class EnergyPointUpdater {

	private final EnergyPointRepository energyPointRepository;

	EnergyPointUpdater(EnergyPointRepository energyPointRepository) {
		this.energyPointRepository = energyPointRepository;
	}

	void update(Energy energy) {
		energyPointRepository.update(energy);
	}
}
