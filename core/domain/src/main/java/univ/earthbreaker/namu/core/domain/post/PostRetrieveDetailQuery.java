package univ.earthbreaker.namu.core.domain.post;

import jakarta.validation.constraints.NotNull;
import univ.earthbreaker.namu.core.domain.common.SelfValidating;

public class PostRetrieveDetailQuery extends SelfValidating<PostRetrieveDetailQuery> {

	private final @NotNull Long memberNo;
	private final @NotNull Long postNo;

	public PostRetrieveDetailQuery(Long memberNo, Long postNo) {
		this.memberNo = memberNo;
		this.postNo = postNo;
		this.validateSelf("memberNo, postNo 는 null 이 될 수 없습니다");
	}

	public Long getMemberNo() {
		return memberNo;
	}

	public Long getPostNo() {
		return postNo;
	}
}
