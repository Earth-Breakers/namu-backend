package univ.earthbreaker.namu.core.service.mission;

public record CertifiedMissionPostCommand(
	Long memberNo,
	String content,
	String imagePathKey,
	Long point
) {
}
