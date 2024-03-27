package univ.earthbreaker.namu.core.domain.point;

import org.springframework.stereotype.Component;

@Component
public class EnergyPointFinder {

	private final EnergyPointRepository energyPointRepository;

	public EnergyPointFinder(EnergyPointRepository energyPointRepository) {
		this.energyPointRepository = energyPointRepository;
	}

	public Energy find(long memberNo) {
		return energyPointRepository.find(memberNo);
	}
}
