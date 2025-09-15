package univ.earthbreaker.namu.core.domain.reaction.infra;

import univ.earthbreaker.namu.core.domain.reaction.ReactionType;
import univ.earthbreaker.namu.core.domain.reaction.TargetType;

public record ReactionDbCommand(
	long memberNo,
	long targetNo,
	TargetType targetType,
	ReactionType reactionType
) {
}
