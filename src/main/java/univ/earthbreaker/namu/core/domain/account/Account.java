package univ.earthbreaker.namu.core.domain.account;

import java.util.Objects;

public class Account {

	private final long no;
	private final String socialId;
	private final SocialType socialType;
	private final long memberNo;

	public Account(long no, String socialId, SocialType socialType, long memberNo) {
		this.no = no;
		this.socialId = socialId;
		this.socialType = socialType;
		this.memberNo = memberNo;
	}

	public long getMemberNo() {
		return memberNo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Account account = (Account)o;
		return no == account.no && memberNo == account.memberNo && socialId.equals(account.socialId)
			&& socialType == account.socialType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(no, socialId, socialType, memberNo);
	}
}
