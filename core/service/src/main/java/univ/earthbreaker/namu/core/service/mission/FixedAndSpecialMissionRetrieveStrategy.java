package univ.earthbreaker.namu.core.service.mission;

import univ.earthbreaker.namu.core.domain.mission.MemberMissionQueryResult;
import univ.earthbreaker.namu.core.domain.mission.MemberMissions;

public class FixedAndSpecialMissionRetrieveStrategy implements MissionRetrieveStrategy {

	@Override
	public MemberMissionQueryResult retrieve(MemberMissions memberMissions) {
		return new MemberMissionQueryResult(
			memberMissions.findTodayMissions(),
			memberMissions.findDefaultMissions(),
			memberMissions.findSpecialMissions()
		);
	}
}
