package univ.earthbreaker.namu.core.service.post;

public interface PostMemberBridge {

	PostMemberDto findMemberInfo(long memberNo);

	record PostMemberDto(long memberNo, String nickname) {
	}
}
