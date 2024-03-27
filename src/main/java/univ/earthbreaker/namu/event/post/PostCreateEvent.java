package univ.earthbreaker.namu.event.post;

public record PostCreateEvent(
	long memberNo,
	String title,
	String content,
	String imagePathKey,
	long missionNo
) {
}
