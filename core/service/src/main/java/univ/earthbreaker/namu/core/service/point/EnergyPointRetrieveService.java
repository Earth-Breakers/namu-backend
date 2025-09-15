package univ.earthbreaker.namu.core.service.point;

import org.springframework.stereotype.Service;

import univ.earthbreaker.namu.core.domain.point.Energy;

@Service
public class EnergyPointRetrieveService {

	private final EnergyPointFinder energyPointFinder;

	public EnergyPointRetrieveService(EnergyPointFinder energyPointFinder) {
		this.energyPointFinder = energyPointFinder;
	}

	public Energy retrieve(long memberNo) {
		return energyPointFinder.find(memberNo);
	}
}
