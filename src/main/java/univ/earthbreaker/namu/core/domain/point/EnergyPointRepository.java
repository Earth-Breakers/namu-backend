package univ.earthbreaker.namu.core.domain.point;

import org.springframework.stereotype.Repository;

import jakarta.validation.constraints.NotNull;

@Repository
public interface EnergyPointRepository {

	@NotNull Energy find(long memberNo);

	void update(PointUpdateDbCommand command);
}
