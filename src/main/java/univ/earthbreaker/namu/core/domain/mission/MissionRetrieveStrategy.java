package univ.earthbreaker.namu.core.domain.mission;

import org.jetbrains.annotations.NotNull;

public interface MissionRetrieveStrategy {
	@NotNull MemberMissionQueryResult retrieve(MemberMission memberMission);
}
