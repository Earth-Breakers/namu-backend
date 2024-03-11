package univ.earthbreaker.namu.core.domain.friend;

public record FriendRelationCommand(
	long memberNo,
	long targetMemberNo
) {
}
