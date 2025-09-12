package univ.earthbreaker.namu.core.domain.mission.infra;

public record ImageUploadCommand(
	String requestId,
	Long memberNo,
	Long missionNo,
	String imagePathKey
) {
}
