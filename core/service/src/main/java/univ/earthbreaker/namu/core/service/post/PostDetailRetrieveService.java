package univ.earthbreaker.namu.core.service.post;

import org.springframework.stereotype.Service;

import univ.earthbreaker.namu.core.domain.post.Post;
import univ.earthbreaker.namu.core.domain.post.PostReactionResult;
import univ.earthbreaker.namu.core.domain.post.PostRetrieveDetailQuery;
import univ.earthbreaker.namu.core.domain.reaction.infra.ReactionBridge;
import univ.earthbreaker.namu.core.domain.reaction.infra.ReactionBridge.ReactionStatus;

@Service
public class PostDetailRetrieveService {

	private final PostFinder postFinder;
	private final ReactionBridge postReactionBridge;

	public PostDetailRetrieveService(PostFinder postFinder, ReactionBridge postReactionBridge) {
		this.postFinder = postFinder;
		this.postReactionBridge = postReactionBridge;
	}

	public PostReactionResult retrieveDetail(PostRetrieveDetailQuery query) {
		Post post = postFinder.find(query.getMemberNo(), query.getPostNo());
		ReactionStatus reactionStatus = postReactionBridge.find(post.getMemberNo(), post.getNo(), "POST");
		return PostReactionResult.of(post, reactionStatus);
	}
}
