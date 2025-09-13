package univ.earthbreaker.namu.core.service.mission;

public record MissionCompleteCommand(
	Long memberNo,
	Long missionNo
) {
}
