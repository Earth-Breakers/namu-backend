package univ.earthbreaker.namu.core.service.mission;

import java.util.List;

import univ.earthbreaker.namu.core.domain.mission.MemberMission;

public record MemberMissionQueryResult(
	List<MemberMission> todayMissions,
	List<MemberMission> defaultMissions,
	List<MemberMission> specialMissions
) {
}
