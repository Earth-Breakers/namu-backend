package univ.earthbreaker.namu.clients.point;

import java.time.Instant;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.server.external.api.point.PointIssueRequest;

@Component
public class ExternalPointManager implements PointManager {

	private final PointApiCaller pointApiCaller;

	public ExternalPointManager(PointApiCaller pointApiCaller) {
		this.pointApiCaller = pointApiCaller;
	}

	@Override
	public Long issuePoint(long memberNo, long missionNo) {
		PointIssueRequest request = new PointIssueRequest(memberNo, missionNo, Instant.now().toEpochMilli());
		try {
			ExternalPointResult result = pointApiCaller.issuePoint(null, request);
			return result.point();
		} catch (Exception e) {
			throw new PointServerServerException(e.getMessage());
		}
	}
}
