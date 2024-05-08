package univ.earthbreaker.namu.core.domain.reaction;

public record ReactionDbCommand(
	long memberNo,
	long targetNo,
	TargetType targetType,
	ReactionType reactionType
) {
}
