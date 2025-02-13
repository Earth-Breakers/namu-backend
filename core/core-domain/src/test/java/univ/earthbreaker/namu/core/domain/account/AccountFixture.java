package univ.earthbreaker.namu.core.domain.account;

public class AccountFixture {

	public static final long ACCOUNT_NO = 1L;
	public static final long MEMBER_NO = 1L;
	public static final String SOCIAL_ID = "socialId";
	public static final String SOCIAL_NICKNAME = "socialNickname";
	public static final String NOTIFICATION_TOKEN = "notificationToken";
	public static final String ACCESS_TOKEN = "accessToken";
	public static final String REFRESH_TOKEN = "refreshToken";
	public static final LoginCommand LOGIN_COMMAND = LoginCommand.builder()
		.socialId(SOCIAL_ID)
		.socialNickname(SOCIAL_NICKNAME)
		.notificationToken(NOTIFICATION_TOKEN)
		.build();
	public static final LoginResult NEW_MEMBER_RESULT = new LoginResult(MEMBER_NO, ACCESS_TOKEN, REFRESH_TOKEN, true);
	public static final LoginResult ALREADY_MEMBER_RESULT = new LoginResult(MEMBER_NO, ACCESS_TOKEN, REFRESH_TOKEN, false);
	public static final Account ACCOUNT = new Account(ACCOUNT_NO, SOCIAL_ID, SocialType.KAKAO, MEMBER_NO);
}
