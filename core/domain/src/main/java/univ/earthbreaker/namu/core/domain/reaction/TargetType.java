package univ.earthbreaker.namu.core.domain.reaction;

public enum TargetType {

	POST("게시글"),
	COMMENT("방명록"),
	;

	private final String value;

	TargetType(String value) {
		this.value = value;
	}
}
