package univ.earthbreaker.namu.app.api.post;

import static univ.earthbreaker.namu.core.domain.common.Constant.*;

import java.util.List;

import univ.earthbreaker.namu.core.domain.post.Post;
import univ.earthbreaker.namu.core.domain.post.RelatedPostResult;

public record RelatedPostFeedResponses(
	List<RelatedPostFeedResponse> posts,
	boolean isLastPage
) {

	static RelatedPostFeedResponses from(RelatedPostResult result) {
		List<RelatedPostFeedResponse> responses = result.posts()
			.stream()
			.map(RelatedPostFeedResponse::from)
			.toList();
		return new RelatedPostFeedResponses(responses, result.isLast());
	}

	record RelatedPostFeedResponse(
		long postNo,
		long memberNo,
		String nickname,
		String title,
		String imageUrl
	) {
		static RelatedPostFeedResponse from(Post post) {
			return new RelatedPostFeedResponse(
				post.getNo(),
				post.getMemberNo(),
				post.getMemberNickname(),
				post.getTitle(),
				IMAGE_ACCESS_URL + post.getImagePath()
			);
		}
	}
}
