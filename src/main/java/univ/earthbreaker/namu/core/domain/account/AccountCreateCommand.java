package univ.earthbreaker.namu.core.domain.account;

public abstract class AccountCreateCommand {

	private final String socialId;
	private final SocialType socialType;
	private final Long memberNo;

	protected AccountCreateCommand(String socialId, SocialType socialType, Long memberNo) {
		this.socialId = socialId;
		this.socialType = socialType;
		this.memberNo = memberNo;
	}

	public String getSocialId() {
		return socialId;
	}

	public SocialType getSocialType() {
		return socialType;
	}

	public Long getMemberNo() {
		return memberNo;
	}
}
