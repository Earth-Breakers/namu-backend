package univ.earthbreaker.namu.core.domain.mission.service;

import java.util.Collections;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.mission.MemberMissions;

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
