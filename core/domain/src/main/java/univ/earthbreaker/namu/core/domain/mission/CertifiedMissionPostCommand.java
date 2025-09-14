package univ.earthbreaker.namu.core.domain.mission;

public record CertifiedMissionPostCommand(
	Long memberNo,
	String content,
	String imagePathKey,
	Long point
) {
}
