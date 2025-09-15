package univ.earthbreaker.namu.app.api.post;

import static univ.earthbreaker.namu.core.domain.common.Constant.*;

import univ.earthbreaker.namu.core.domain.post.Post;
import univ.earthbreaker.namu.core.domain.post.PostReactionResult.PostReactionStatus;

public record PostDetailResponse(
	long postNo,
	long memberNo,
	String nickname,
	String title,
	String content,
	String imageUrl,
	long relatedMissionNo,
	PostReactionStatus reactionStatus
) {
	static PostDetailResponse of(Post post, PostReactionStatus reactionStatus) {
		return new PostDetailResponse(
			post.getNo(),
			post.getMemberNo(),
			post.getMemberNickname(),
			post.getTitle(),
			post.getContent(),
			IMAGE_ACCESS_URL + post.getImagePath(),
			post.getMissionNo(),
			reactionStatus
		);
	}
}
