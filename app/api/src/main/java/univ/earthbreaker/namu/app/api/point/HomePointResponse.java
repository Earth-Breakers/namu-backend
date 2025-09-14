package univ.earthbreaker.namu.app.api.point;

import univ.earthbreaker.namu.core.domain.point.Energy;

public record HomePointResponse(int point) {
	static HomePointResponse from(Energy energyPoint) {
		return new HomePointResponse(energyPoint.getPointValue());
	}
}
