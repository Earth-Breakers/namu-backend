package univ.earthbreaker.namu.core.service.auth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import univ.earthbreaker.namu.core.domain.auth.RefreshToken;
import univ.earthbreaker.namu.core.domain.auth.UnAuthorizedException;
import univ.earthbreaker.namu.core.domain.auth.infra.RefreshTokenRepository;

@ExtendWith(MockitoExtension.class)
class RefreshTokenFinderTest {

	private @Mock RefreshTokenRepository refreshTokenRepository;
	private @InjectMocks RefreshTokenFinder refreshTokenFinder;

	@DisplayName("refreshTokenValue 에 해당하는 RefreshToken 객체를 반환한다")
	@Test
	void find() {
		// given
		when(refreshTokenRepository.findOrNull(RefreshTokenFixture.REFRESH_TOKEN_VALUE))
			.thenReturn(RefreshTokenFixture.NEVER_EXPIRED_REFRESH_TOKEN);

		// when
		RefreshToken refreshToken = refreshTokenFinder.find(RefreshTokenFixture.REFRESH_TOKEN_VALUE);

		// then
		assertAll(
			() -> AssertionsForClassTypes.assertThat(refreshToken).isNotNull(),
			() -> AssertionsForClassTypes.assertThat(refreshToken.getValue()).isEqualTo(
				RefreshTokenFixture.REFRESH_TOKEN_VALUE),
			() -> AssertionsForClassTypes.assertThat(refreshToken.getMemberNo()).isEqualTo(RefreshTokenFixture.MEMBER_NO),
			() -> AssertionsForClassTypes.assertThat(refreshToken.getExpiresIn()).isEqualTo(
				RefreshTokenFixture.NEVER_EXPIRES)
		);
	}

	@DisplayName("refreshTokenValue 에 해당하는 RefreshToken 를 찾지 못하면 예외를 발생시킨다")
	@Test
	void fail_find() {
		// given
		when(refreshTokenRepository.findOrNull(RefreshTokenFixture.REFRESH_TOKEN_VALUE))
			.thenReturn(null);

		// when, then
		assertThatThrownBy(() -> refreshTokenFinder.find(RefreshTokenFixture.REFRESH_TOKEN_VALUE))
			.isInstanceOf(UnAuthorizedException.class)
			.hasMessage(UnAuthorizedException.notFound(RefreshTokenFixture.REFRESH_TOKEN_VALUE).getMessage());
	}
}
