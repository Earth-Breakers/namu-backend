package univ.earthbreaker.namu.core.auth;

import java.time.LocalDateTime;

public class RefreshTokenFixture {

	static final long REFRESH_TOKEN_NO = 1L;
	static final String REFRESH_TOKEN_VALUE = "refreshTokenValue";
	static final LocalDateTime EXPIRES_IN = LocalDateTime.MAX;
	static final long MEMBER_NO = 1L;

	static final RefreshToken REFRESH_TOKEN = new RefreshToken(
		REFRESH_TOKEN_NO,
		REFRESH_TOKEN_VALUE,
		EXPIRES_IN,
		MEMBER_NO
	);
}
