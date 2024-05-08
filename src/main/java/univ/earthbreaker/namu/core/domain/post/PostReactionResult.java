package univ.earthbreaker.namu.core.domain.post;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.reaction.ReactionBridge.ReactionStatus;

public record PostReactionResult(
	Post post,
	PostReactionStatus reactionStatus
) {

	static @NotNull PostReactionResult of(Post post, @NotNull ReactionStatus reactionStatus) {
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
