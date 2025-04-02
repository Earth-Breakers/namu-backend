package univ.earthbreaker.namu.event.character;

public record AddEnergyPointEvent(
	long memberNo,
	int pointValue,
	String energyType
) {
}
