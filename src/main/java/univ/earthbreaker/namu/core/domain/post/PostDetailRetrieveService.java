package univ.earthbreaker.namu.core.domain.post;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import univ.earthbreaker.namu.core.domain.reaction.ReactionBridge;
import univ.earthbreaker.namu.core.domain.reaction.ReactionBridge.ReactionStatus;

@Service
public class PostDetailRetrieveService {

	private final PostFinder postFinder;
	private final ReactionBridge postReactionBridge;

	public PostDetailRetrieveService(PostFinder postFinder, ReactionBridge postReactionBridge) {
		this.postFinder = postFinder;
		this.postReactionBridge = postReactionBridge;
	}

	public PostReactionResult retrieveDetail(@NotNull PostRetrieveDetailQuery query) {
		Post post = postFinder.find(query.getMemberNo(), query.getPostNo());
		ReactionStatus reactionStatus = postReactionBridge.find(post.getMemberNo(), post.getNo(), "POST");
		return PostReactionResult.of(post, reactionStatus);
	}
}
