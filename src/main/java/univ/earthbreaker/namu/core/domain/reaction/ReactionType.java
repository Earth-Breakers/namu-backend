package univ.earthbreaker.namu.core.domain.reaction;

public enum ReactionType {

	LIKE("좋아요"),
	DISLIKE("싫어요"),
	;

	private final String value;

	ReactionType(String value) {
		this.value = value;
	}
}
