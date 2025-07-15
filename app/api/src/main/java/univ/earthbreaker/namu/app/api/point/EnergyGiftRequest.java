package univ.earthbreaker.namu.app.api.point;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.point.EnergyGiftCommand;

public record EnergyGiftRequest(
	Integer pointValue
) {
	@NotNull EnergyGiftCommand toCommand(Long memberNo, Long targetMemberNo) {
		return new EnergyGiftCommand(memberNo, targetMemberNo, pointValue);
	}
}
