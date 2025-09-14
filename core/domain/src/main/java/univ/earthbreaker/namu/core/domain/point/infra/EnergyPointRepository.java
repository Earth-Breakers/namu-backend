package univ.earthbreaker.namu.core.domain.point.infra;

import univ.earthbreaker.namu.core.domain.point.Energy;

public interface EnergyPointRepository {

	Energy find(long memberNo);

	void updatePoint(PointUpdateDbCommand command);

	void receivePoint(PointUpdateDbCommand command);

	void register(long memberNo);
}
