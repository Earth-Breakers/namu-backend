package univ.earthbreaker.namu.core.domain.point.infra;

public record PointUpdateDbCommand(
	long memberNo,
	int point
) {
}
