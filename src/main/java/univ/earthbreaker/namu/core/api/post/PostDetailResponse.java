package univ.earthbreaker.namu.core.api.post;

import static univ.earthbreaker.namu.core.domain.common.Constant.IMAGE_ACCESS_URL;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.post.Post;

public record PostDetailResponse(
	long postNo,
	String title,
	String content,
	String imageUrl
) {
	static @NotNull PostDetailResponse from(@NotNull Post post) {
		return new PostDetailResponse(
			post.getNo(),
			post.getTitle(),
			post.getContent(),
			IMAGE_ACCESS_URL + post.getImagePath()
		);
	}
}
