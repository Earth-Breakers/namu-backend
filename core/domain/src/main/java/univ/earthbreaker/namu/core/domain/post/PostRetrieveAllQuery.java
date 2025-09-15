package univ.earthbreaker.namu.core.domain.post;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import univ.earthbreaker.namu.core.domain.common.SelfValidating;

public class PostRetrieveAllQuery extends SelfValidating<PostRetrieveAllQuery> {

	private final @NotNull Long memberNo;
	private final @NotNull LocalDate date;

	public PostRetrieveAllQuery(Long memberNo, LocalDate date) {
		this.memberNo = memberNo;
		this.date = date;
		this.validateSelf("memberNo, date 는 null 이 될 수 없습니다");
	}

	public Long getMemberNo() {
		return memberNo;
	}

	public LocalDate getDate() {
		return date;
	}
}
