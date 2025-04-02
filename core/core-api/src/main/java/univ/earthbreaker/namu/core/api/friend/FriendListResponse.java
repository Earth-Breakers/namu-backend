package univ.earthbreaker.namu.core.api.friend;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.member.friend.Following;
import univ.earthbreaker.namu.core.domain.member.friend.Friend;

public record FriendListResponse(
	List<FriendResponse> results
) {
	static @NotNull FriendListResponse from(@NotNull Friend friend) {
		List<FriendResponse> friendResponses = friend.getFollowings()
			.stream()
			.map(FriendResponse::toResult)
			.toList();
		return new FriendListResponse(friendResponses);
	}

	record FriendResponse(
		long memberNo,
		String nickname,
		int level
	) {
		private static @NotNull FriendListResponse.FriendResponse toResult(@NotNull Following following) {
			return new FriendResponse(following.getFollowerNo(), following.getNickname(), following.getLevel());
		}
	}
}
