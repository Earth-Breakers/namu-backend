package univ.earthbreaker.namu.core.domain.mission;

public record MissionCompleteCommand(
	Long memberNo,
	Long missionNo
) {
}
