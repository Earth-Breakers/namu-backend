package univ.earthbreaker.namu.core.domain.post;

public interface PostMemberBridge {

	PostMemberDto findMemberInfo(long memberNo);

	record PostMemberDto(long memberNo, String nickname) {
	}
}
