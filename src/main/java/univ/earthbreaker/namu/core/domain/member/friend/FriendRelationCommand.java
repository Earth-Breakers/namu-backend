package univ.earthbreaker.namu.core.domain.member.friend;

public record FriendRelationCommand(
	long memberNo,
	long targetMemberNo
) {
}
