package univ.earthbreaker.namu.core.domain.mission;

import java.util.List;

public record MemberMissionQueryResult(
	List<MemberMission> todayMissions,
	List<MemberMission> defaultMissions,
	List<MemberMission> specialMissions
) {
}
