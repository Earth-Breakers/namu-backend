package univ.earthbreaker.namu.app.api.point;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.point.Energy;

public record HomePointResponse(
	int point
) {
	static @NotNull HomePointResponse from(@NotNull Energy energyPoint) {
		return new HomePointResponse(energyPoint.getPointValue());
	}
}
