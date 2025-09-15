package univ.earthbreaker.namu.core.domain.post;

import static univ.earthbreaker.namu.core.domain.reaction.infra.ReactionBridge.ReactionStatus;

import java.util.List;

public record PostReactionResult(
	Post post,
	PostReactionStatus reactionStatus
) {

	public static PostReactionResult of(Post post, ReactionStatus reactionStatus) {
		List<PostReactionStatus.Info> infos = reactionStatus.statuses()
			.stream()
			.map(status -> new PostReactionStatus.Info(
				status.reactionType(),
				status.reactionCount(),
				status.reactionMembers()
			))
			.toList();
		return new PostReactionResult(post, new PostReactionStatus(infos, reactionStatus.alreadyReaction()));
	}

	public record PostReactionStatus(
		List<Info> infos,
		boolean alreadyReaction
	) {
		public record Info(
			String reactionType,
			int reactionCount,
			List<Long> reactionMembers
		) {
		}
	}
}
