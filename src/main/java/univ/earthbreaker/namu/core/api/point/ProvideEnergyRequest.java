package univ.earthbreaker.namu.core.api.point;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.point.ProvideEnergyPointCommand;

public record ProvideEnergyRequest(
	String energyType,
	Integer pointValue
) {
	@NotNull ProvideEnergyPointCommand toCommand(Long memberNo) {
		return new ProvideEnergyPointCommand(memberNo, pointValue, energyType);
	}
}
