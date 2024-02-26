package univ.earthbreaker.namu.core.auth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static univ.earthbreaker.namu.core.auth.RefreshTokenFixture.NEVER_EXPIRED_REFRESH_TOKEN;
import static univ.earthbreaker.namu.core.auth.RefreshTokenFixture.REFRESH_TOKEN_VALUE;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TokenReissueServiceTest {

	private static final Long PAYLOAD = 1L;
	private static final String ACCESS_TOKEN = "accessToken";

	private @Mock RefreshTokenFinder refreshTokenFinder;
	private @Mock RefreshTokenValidator refreshTokenValidator;
	private @Mock JwtManager jwtManager;
	private @InjectMocks TokenReissueService tokenReissueService;

	@DisplayName("refreshTokenValue 를 받아 해당 RefreshToken 객체를 검증하고, 이상이 없다면 accessToken 을 재발급한다")
	@Test
	void reissue() {
		// given
		when(refreshTokenFinder.find(REFRESH_TOKEN_VALUE))
			.thenReturn(NEVER_EXPIRED_REFRESH_TOKEN);
		when(jwtManager.createAccessToken(PAYLOAD))
			.thenReturn(ACCESS_TOKEN);

		// when
		TokenResult tokenResult = tokenReissueService.reissue(REFRESH_TOKEN_VALUE);

		// then
		assertAll(
			() -> assertThat(tokenResult.accessToken()).isEqualTo(ACCESS_TOKEN),
			() -> verify(refreshTokenValidator).validate(NEVER_EXPIRED_REFRESH_TOKEN)
		);
	}
}
