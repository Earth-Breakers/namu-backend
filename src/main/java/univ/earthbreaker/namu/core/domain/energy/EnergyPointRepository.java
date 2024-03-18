package univ.earthbreaker.namu.core.domain.energy;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

@Repository
public interface EnergyPointRepository {

	@NotNull Energy find(long memberNo);

	void update(Energy energy);
}
