package univ.earthbreaker.namu.core.domain.reaction.infra;

import java.util.List;

public interface ReactionBridge {

	ReactionStatus find(long memberNo, long targetNo, String targetTypeName);

	record ReactionStatus(
		List<Status> statuses,
		boolean alreadyReaction
	) {
		public record Status(
			String reactionType,
			int reactionCount,
			List<Long> reactionMembers
		) {
		}
	}
}
