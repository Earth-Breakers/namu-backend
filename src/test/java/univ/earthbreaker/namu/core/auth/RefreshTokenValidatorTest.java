package univ.earthbreaker.namu.core.auth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static univ.earthbreaker.namu.core.auth.RefreshTokenFixture.ALWAYS_EXPIRED_REFRESH_TOKEN;
import static univ.earthbreaker.namu.core.auth.RefreshTokenFixture.NEVER_EXPIRED_REFRESH_TOKEN;
import static univ.earthbreaker.namu.core.auth.RefreshTokenFixture.REFRESH_TOKEN_VALUE;

import java.time.Clock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RefreshTokenValidatorTest {

	private static final Clock CLOCK = Clock.systemDefaultZone();

	private final RefreshTokenValidator refreshTokenValidator = new RefreshTokenValidator(CLOCK);

	@DisplayName("refreshToken 의 만료 시간을 검증하고, 만료되지 않았다면 아무 예외도 발생하지 않는다")
	@Test
	void validate() {
		// when, then
		assertThatNoException()
			.isThrownBy(() -> refreshTokenValidator.validate(NEVER_EXPIRED_REFRESH_TOKEN));
	}

	@DisplayName("refreshToken 의 만료 시간을 검증하고, 만료되었다면 예외를 발생시킨다")
	@Test
	void fail_validate() {
		// when, then
		assertThatThrownBy(() -> refreshTokenValidator.validate(ALWAYS_EXPIRED_REFRESH_TOKEN))
			.isInstanceOf(UnAuthorizedException.class)
			.hasMessage(UnAuthorizedException.expired(REFRESH_TOKEN_VALUE).getMessage());
	}
}
