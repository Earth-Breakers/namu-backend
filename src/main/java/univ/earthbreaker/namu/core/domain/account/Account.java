package univ.earthbreaker.namu.core.domain.account;

import java.util.Objects;

public class Account {

	private final Long no;
	private final String socialId;
	private final SocialType socialType;
	private final Long memberNo;

	public Account(Long no, String socialId, SocialType socialType, Long memberNo) {
		this.no = no;
		this.socialId = socialId;
		this.socialType = socialType;
		this.memberNo = memberNo;
	}

	public Long getMemberNo() {
		return memberNo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Account account = (Account)o;
		return no.equals(account.no) && socialId.equals(account.socialId) && memberNo.equals(account.memberNo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(no, socialId, memberNo);
	}
}
