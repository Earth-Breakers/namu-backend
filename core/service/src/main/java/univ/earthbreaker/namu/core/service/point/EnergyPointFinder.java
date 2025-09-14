package univ.earthbreaker.namu.core.service.point;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.point.Energy;
import univ.earthbreaker.namu.core.domain.point.infra.EnergyPointRepository;

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
