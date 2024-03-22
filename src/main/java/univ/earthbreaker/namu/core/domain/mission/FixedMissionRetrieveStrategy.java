package univ.earthbreaker.namu.core.domain.mission;

import java.util.Collections;

import org.jetbrains.annotations.NotNull;

public class FixedMissionRetrieveStrategy implements MissionRetrieveStrategy {

	@Override
	public @NotNull MemberMissionQueryResult retrieve(@NotNull MemberMission memberMission) {
		return new MemberMissionQueryResult(
			memberMission.findTodayMissionList(),
			memberMission.findDefaultMissionList(),
			Collections.emptyList()
		);
	}
}
