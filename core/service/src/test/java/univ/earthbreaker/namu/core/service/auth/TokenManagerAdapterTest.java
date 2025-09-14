package univ.earthbreaker.namu.core.service.auth;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import univ.earthbreaker.namu.core.domain.auth.infra.RefreshTokenRepository;

@ExtendWith(MockitoExtension.class)
class TokenManagerAdapterTest {

	private static final Long PAYLOAD = 1L;
	private static final Long MEMBER_NO = 1L;
	private static final String ACCESS_TOKEN = "accessToken";

	private @Mock JwtManager jwtManager;
	private @Mock RefreshTokenRepository refreshTokenRepository;
	private @InjectMocks TokenManagerAdapter tokenManagerAdapter;

	@DisplayName("payload 를 받아 accessToken 를 생성하고 그 값을 반환한다")
	@Test
	void createAccessToken() {
		// given
		when(jwtManager.createAccessToken(PAYLOAD))
			.thenReturn(ACCESS_TOKEN);

		// when
		String accessToken = tokenManagerAdapter.createAccessToken(PAYLOAD);

		// then
		assertThat(accessToken).isEqualTo(ACCESS_TOKEN);
	}

	@DisplayName("memberNo 를 받아 refreshToken 을 생성하고 그 값을 반환한다")
	@Test
	void createRefreshToken() {
		// given
		when(jwtManager.createRefreshToken(MEMBER_NO))
			.thenReturn(RefreshTokenFixture.NEVER_EXPIRED_REFRESH_TOKEN);

		// when
		String refreshToken = tokenManagerAdapter.createRefreshToken(MEMBER_NO);

		// then
		assertAll(
			() -> assertThat(refreshToken).isEqualTo(RefreshTokenFixture.NEVER_EXPIRED_REFRESH_TOKEN.getValue()),
			() -> verify(refreshTokenRepository).register(RefreshTokenFixture.NEVER_EXPIRED_REFRESH_TOKEN)
		);
	}

	@DisplayName("memberNo 를 받아 해당 member 의 refreshToken 을 업데이트하고, 업데이트된 refreshToken 값을 반환환다")
	@Test
	void updateRefreshToken() {
		// given
		when(jwtManager.createRefreshToken(MEMBER_NO))
			.thenReturn(RefreshTokenFixture.NEVER_EXPIRED_REFRESH_TOKEN);

		// when
		String updatedRefreshToken = tokenManagerAdapter.updateRefreshToken(MEMBER_NO);

		// then
		assertAll(
			() -> assertThat(updatedRefreshToken).isEqualTo(RefreshTokenFixture.NEVER_EXPIRED_REFRESH_TOKEN.getValue()),
			() -> verify(refreshTokenRepository).update(RefreshTokenFixture.NEVER_EXPIRED_REFRESH_TOKEN, MEMBER_NO)
		);
	}
}
