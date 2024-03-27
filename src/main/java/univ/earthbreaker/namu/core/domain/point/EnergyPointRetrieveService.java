package univ.earthbreaker.namu.core.domain.point;

import org.springframework.stereotype.Service;

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
