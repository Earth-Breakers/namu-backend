package univ.earthbreaker.namu.database.core.point;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import univ.earthbreaker.namu.core.domain.point.Energy;
import univ.earthbreaker.namu.core.domain.point.EnergyPointRepository;
import univ.earthbreaker.namu.core.domain.point.PointUpdateDbCommand;

@Repository
public class EnergyPointRepositoryAdapter implements EnergyPointRepository {

	private final EnergyPointJpaRepository energyPointJpaRepository;

	public EnergyPointRepositoryAdapter(EnergyPointJpaRepository energyPointJpaRepository) {
		this.energyPointJpaRepository = energyPointJpaRepository;
	}

	@Override
	public Energy find(long memberNo) {
		return energyPointJpaRepository.findByMemberNo(memberNo)
			.toEnergy();
	}

	@Override
	public void update(@NotNull PointUpdateDbCommand command) {
		energyPointJpaRepository.update(command.memberNo(), command.point());
	}
}
