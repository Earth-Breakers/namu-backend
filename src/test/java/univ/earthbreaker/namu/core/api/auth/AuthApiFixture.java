package univ.earthbreaker.namu.core.api.auth;

import univ.earthbreaker.namu.core.domain.account.LoginResult;
import univ.earthbreaker.namu.external.oauth.OAuthMemberInfoResult;

public class AuthApiFixture {

	public static final String SOCIAL_TOKEN = "socialToken";
	public static final String NOTIFICATION_TOKEN = "notificationToken";
	public static final LoginRequest LOGIN_REQUEST = new LoginRequest(SOCIAL_TOKEN, NOTIFICATION_TOKEN);

	public static final OAuthMemberInfoResult O_AUTH_RESULT = new OAuthMemberInfoResult("id", "nickname");

	public static final String ACCESS_TOKEN = "accessTokenValue";
	public static final String REFRESH_TOKEN = "refreshTokenValue";
	public static final LoginResult NEW_MEMBER_LOGIN_RESULT = new LoginResult(ACCESS_TOKEN, REFRESH_TOKEN, true);
	public static final LoginResult ALREADY_MEMBER_LOGIN_RESULT = new LoginResult(ACCESS_TOKEN, REFRESH_TOKEN, false);
}
