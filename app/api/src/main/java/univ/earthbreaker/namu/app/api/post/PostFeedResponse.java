package univ.earthbreaker.namu.core.api.post;

import static univ.earthbreaker.namu.core.domain.common.Constant.IMAGE_ACCESS_URL;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.post.Post;

public record PostFeedResponse(
	long postNo,
	String title,
	String imageUrl
) {
	static @NotNull PostFeedResponse from(@NotNull Post post) {
		return new PostFeedResponse(post.getNo(), post.getTitle(), IMAGE_ACCESS_URL + post.getImagePath());
	}
}
