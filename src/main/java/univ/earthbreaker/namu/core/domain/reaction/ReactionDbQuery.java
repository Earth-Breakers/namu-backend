package univ.earthbreaker.namu.core.domain.reaction;

public record ReactionDbQuery(
	long memberNo,
	long targetNo,
	TargetType targetType
) {
}
