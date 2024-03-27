package univ.earthbreaker.namu.core.api.mission;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.mission.MemberMissionQueryResult;
import univ.earthbreaker.namu.core.domain.mission.MemberMission;
import univ.earthbreaker.namu.core.domain.mission.MissionStatus;

public record MemberMissionResponse(
	List<MissionResponse> todayMissions,
	List<MissionResponse> defaultMissions,
	List<MissionResponse> specialMissions
) {
	static @NotNull MemberMissionResponse from(@NotNull MemberMissionQueryResult result) {
		return new MemberMissionResponse(
			result.todayMissions().stream().map(MissionResponse::toResponse).toList(),
			result.defaultMissions().stream().map(MissionResponse::toResponse).toList(),
			result.specialMissions().stream().map(MissionResponse::toResponse).toList()
		);
	}

	record MissionResponse(
		long missionNo,
		String title,
		MissionStatus status
	) {
		static @NotNull MissionResponse toResponse(@NotNull MemberMission mission) {
			return new MissionResponse(mission.getNo(), mission.getTitle(), mission.getStatus());
		}
	}
}
