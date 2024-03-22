package univ.earthbreaker.namu.core.domain.mission;

import java.util.List;

public record MemberMissionQueryResult(
	List<Mission> todayMissions,
	List<Mission> defaultMissions,
	List<Mission> specialMissions
) {
}
