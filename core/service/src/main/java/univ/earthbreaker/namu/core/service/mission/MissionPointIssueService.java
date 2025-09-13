package univ.earthbreaker.namu.core.domain.mission.service;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.mission.infra.PointManager;

@Component
public class MissionPointIssueService {

	private final PointManager pointManager;

	public MissionPointIssueService(PointManager pointManager) {
		this.pointManager = pointManager;
	}

	public PointIssueResult process(long memberNo, long missionNo) {
		try {
			Long point = pointManager.issue(memberNo, missionNo);
			return new PointIssueResult(point, true);
		} catch (PointIssueProcessException e) {
			return new PointIssueResult(0, false);
		}
	}
}
