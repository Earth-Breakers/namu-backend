package univ.earthbreaker.namu.core.auth;

import java.time.LocalDateTime;

public class RefreshTokenFixture {

	static final long REFRESH_TOKEN_NO = 1L;
	static final String REFRESH_TOKEN_VALUE = "refreshTokenValue";
	static final LocalDateTime NEVER_EXPIRES = LocalDateTime.MAX;
	static final LocalDateTime ALWAYS_EXPIRES = LocalDateTime.MIN;
	static final long MEMBER_NO = 1L;

	static final RefreshToken NEVER_EXPIRED_REFRESH_TOKEN = new RefreshToken(
		REFRESH_TOKEN_NO,
		REFRESH_TOKEN_VALUE,
		NEVER_EXPIRES,
		MEMBER_NO
	);

	static final RefreshToken ALWAYS_EXPIRED_REFRESH_TOKEN = new RefreshToken(
		REFRESH_TOKEN_NO,
		REFRESH_TOKEN_VALUE,
		ALWAYS_EXPIRES,
		MEMBER_NO
	);
}
