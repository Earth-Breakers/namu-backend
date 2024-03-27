package univ.earthbreaker.namu.database.core.point;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import univ.earthbreaker.namu.core.domain.point.PointRepository;
import univ.earthbreaker.namu.core.domain.point.PointUpdateDbCommand;

@Repository
public class EnergyPointRepositoryAdapter implements PointRepository {

	private final EnergyPointJpaRepository energyPointJpaRepository;

	public EnergyPointRepositoryAdapter(EnergyPointJpaRepository energyPointJpaRepository) {
		this.energyPointJpaRepository = energyPointJpaRepository;
	}

	@Override
	public void update(@NotNull PointUpdateDbCommand command) {
		energyPointJpaRepository.update(command.memberNo(), command.point());
	}
}
