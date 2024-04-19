package univ.earthbreaker.namu.core.domain.post;

public record PostCreateDbCommand(
	long memberNo,
	String nickname,
	String title,
	String content,
	String imagePathKey,
	long missionNo
) {
}
