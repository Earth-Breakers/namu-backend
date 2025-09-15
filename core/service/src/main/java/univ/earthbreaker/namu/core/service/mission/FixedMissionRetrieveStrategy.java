package univ.earthbreaker.namu.core.service.mission;

import java.util.Collections;

import univ.earthbreaker.namu.core.domain.mission.MemberMissionQueryResult;
import univ.earthbreaker.namu.core.domain.mission.MemberMissions;

public class FixedMissionRetrieveStrategy implements MissionRetrieveStrategy {

	@Override
	public MemberMissionQueryResult retrieve(MemberMissions memberMissions) {
		return new MemberMissionQueryResult(
			memberMissions.findTodayMissions(),
			memberMissions.findDefaultMissions(),
			Collections.emptyList()
		);
	}
}
