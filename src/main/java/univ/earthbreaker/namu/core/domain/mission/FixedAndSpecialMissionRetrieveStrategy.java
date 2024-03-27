package univ.earthbreaker.namu.core.domain.mission;

import org.jetbrains.annotations.NotNull;

public class FixedAndSpecialMissionRetrieveStrategy implements MissionRetrieveStrategy {

	@Override
	public @NotNull MemberMissionQueryResult retrieve(@NotNull MemberMissions memberMissions) {
		return new MemberMissionQueryResult(
			memberMissions.findTodayMissions(),
			memberMissions.findDefaultMissions(),
			memberMissions.findSpecialMissions()
		);
	}
}
