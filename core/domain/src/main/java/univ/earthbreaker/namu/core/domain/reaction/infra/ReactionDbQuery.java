package univ.earthbreaker.namu.core.domain.reaction.infra;

import univ.earthbreaker.namu.core.domain.reaction.TargetType;

public record ReactionDbQuery(
	long memberNo,
	long targetNo,
	TargetType targetType
) {
}
