package univ.earthbreaker.namu.core.domain.account;

public class AccountFixture {

	private static final long ACCOUNT_NO = 1L;
	private static final long MEMBER_NO = 1L;
	private static final String SOCIAL_ID = "socialId";

	static final Account ACCOUNT = new Account(ACCOUNT_NO, SOCIAL_ID, SocialType.KAKAO, MEMBER_NO);
}
