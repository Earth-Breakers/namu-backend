package univ.earthbreaker.namu.app.api.point;

import univ.earthbreaker.namu.core.domain.point.ProvideEnergyPointCommand;

public record ProvideEnergyRequest(
	String energyType,
	Integer pointValue
) {
	ProvideEnergyPointCommand toCommand(Long memberNo) {
		return new ProvideEnergyPointCommand(memberNo, pointValue, energyType);
	}
}
