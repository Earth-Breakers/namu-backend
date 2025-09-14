package univ.earthbreaker.namu.app.api.post;

import static univ.earthbreaker.namu.core.domain.common.Constant.IMAGE_ACCESS_URL;

import univ.earthbreaker.namu.core.domain.post.Post;

public record PostFeedResponse(
	long postNo,
	String title,
	String imageUrl
) {
	static PostFeedResponse from(Post post) {
		return new PostFeedResponse(post.getNo(), post.getTitle(), IMAGE_ACCESS_URL + post.getImagePath());
	}
}
