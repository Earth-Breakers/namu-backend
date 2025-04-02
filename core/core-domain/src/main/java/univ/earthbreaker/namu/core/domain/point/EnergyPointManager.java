package univ.earthbreaker.namu.core.domain.point;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class EnergyPointManager {

	private final EnergyPointFinder energyPointFinder;
	private final EnergyPointRepository energyPointRepository;

	public EnergyPointManager(EnergyPointFinder energyPointFinder, EnergyPointRepository energyPointRepository) {
		this.energyPointFinder = energyPointFinder;
		this.energyPointRepository = energyPointRepository;
	}

	@Transactional(propagation = Propagation.MANDATORY)
	public void useEnergyPoint(long memberNo, int pointValue) {
		Energy energy = energyPointFinder.find(memberNo);
		Energy useAfterEnergy = energy.use(pointValue);
		energyPointRepository.updatePoint(new PointUpdateDbCommand(memberNo, useAfterEnergy.getPointValue()));
	}

	@Transactional
	public void transfer(long memberNo, long targetMemberNo, int pointValue) {
		Energy myEnergy = energyPointFinder.find(memberNo).use(pointValue);
		Energy targetEnergy = energyPointFinder.find(targetMemberNo).receive(pointValue);
		energyPointRepository.updatePoint(new PointUpdateDbCommand(memberNo, myEnergy.getPointValue()));
		energyPointRepository.updatePoint(new PointUpdateDbCommand(targetMemberNo, targetEnergy.getPointValue()));
	}
}
