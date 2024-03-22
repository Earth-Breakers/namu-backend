package univ.earthbreaker.namu.core.domain.mission;

import org.jetbrains.annotations.NotNull;

public class FixedAndSpecialMissionRetrieveStrategy implements MissionRetrieveStrategy {

	@Override
	public @NotNull MemberMissionQueryResult retrieve(@NotNull MemberMission memberMission) {
		return new MemberMissionQueryResult(
			memberMission.findTodayMissionList(),
			memberMission.findDefaultMissionList(),
			memberMission.findSpecialMissionList()
		);
	}
}
