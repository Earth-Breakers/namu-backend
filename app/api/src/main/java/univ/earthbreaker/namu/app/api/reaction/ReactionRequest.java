package univ.earthbreaker.namu.app.api.reaction;

import univ.earthbreaker.namu.core.domain.reaction.ReactionCommand;

public record ReactionRequest(
	Long targetNo,
	String targetType,
	String reactionType
) {
	ReactionCommand toCommand(Long memberNo) {
		return new ReactionCommand(memberNo, targetNo, targetType, reactionType);
	}
}
