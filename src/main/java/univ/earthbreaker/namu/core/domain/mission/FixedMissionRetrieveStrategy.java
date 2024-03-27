package univ.earthbreaker.namu.core.domain.mission;

import java.util.Collections;

import org.jetbrains.annotations.NotNull;

public class FixedMissionRetrieveStrategy implements MissionRetrieveStrategy {

	@Override
	public @NotNull MemberMissionQueryResult retrieve(@NotNull MemberMissions memberMissions) {
		return new MemberMissionQueryResult(
			memberMissions.findTodayMissions(),
			memberMissions.findDefaultMissions(),
			Collections.emptyList()
		);
	}
}
