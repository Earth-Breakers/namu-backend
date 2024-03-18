package univ.earthbreaker.namu.database.core.energy;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import univ.earthbreaker.namu.core.domain.energy.Energy;
import univ.earthbreaker.namu.core.domain.energy.EnergyPointRepository;

@Repository
public class EnergyPointRepositoryAdapter implements EnergyPointRepository {

	private final EnergyPointJpaRepository energyPointJpaRepository;

	public EnergyPointRepositoryAdapter(EnergyPointJpaRepository energyPointJpaRepository) {
		this.energyPointJpaRepository = energyPointJpaRepository;
	}

	@Override
	public @NotNull Energy find(long memberNo) {
		return energyPointJpaRepository.findByMemberNo(memberNo)
			.toEnergy();
	}

	@Override
	public void update(@NotNull Energy energy) {
		energyPointJpaRepository.update(energy.getNo(), energy.getPointValue());
	}
}
