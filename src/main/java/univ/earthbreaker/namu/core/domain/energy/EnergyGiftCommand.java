package univ.earthbreaker.namu.core.domain.energy;

public record EnergyGiftCommand(
	long memberNo,
	long targetMemberNo,
	int pointValue
) {
}
