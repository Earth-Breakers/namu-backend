package univ.earthbreaker.namu.core.domain.mission.service;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.mission.MemberMissions;

public interface MissionRetrieveStrategy {
	@NotNull MemberMissionQueryResult retrieve(@NotNull MemberMissions memberMission);
}
