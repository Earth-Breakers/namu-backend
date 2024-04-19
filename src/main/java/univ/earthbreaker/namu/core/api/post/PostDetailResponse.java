package univ.earthbreaker.namu.core.api.post;

import static univ.earthbreaker.namu.core.domain.common.Constant.IMAGE_ACCESS_URL;

import org.jetbrains.annotations.NotNull;

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
	static @NotNull PostDetailResponse of(@NotNull Post post, @NotNull PostReactionStatus reactionStatus) {
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
