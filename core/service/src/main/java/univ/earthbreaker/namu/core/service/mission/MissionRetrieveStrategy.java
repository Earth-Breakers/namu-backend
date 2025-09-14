package univ.earthbreaker.namu.core.service.mission;

import univ.earthbreaker.namu.core.domain.mission.MemberMissionQueryResult;
import univ.earthbreaker.namu.core.domain.mission.MemberMissions;

public interface MissionRetrieveStrategy {
	MemberMissionQueryResult retrieve(MemberMissions memberMission);
}
