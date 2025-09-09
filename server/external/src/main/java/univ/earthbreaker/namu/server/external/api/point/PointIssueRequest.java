package univ.earthbreaker.namu.server.external.api.point;

public record PointIssueRequest(
	long userId,
	long missionId,
	long timestamp
) {
}
