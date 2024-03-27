package univ.earthbreaker.namu.core.domain.point;

import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository {
	void update(PointUpdateDbCommand command);
}
