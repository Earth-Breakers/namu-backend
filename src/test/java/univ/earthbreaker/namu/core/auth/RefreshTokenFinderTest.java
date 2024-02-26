package univ.earthbreaker.namu.core.auth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;
import static univ.earthbreaker.namu.core.auth.RefreshTokenFixture.EXPIRES_IN;
import static univ.earthbreaker.namu.core.auth.RefreshTokenFixture.MEMBER_NO;
import static univ.earthbreaker.namu.core.auth.RefreshTokenFixture.REFRESH_TOKEN;
import static univ.earthbreaker.namu.core.auth.RefreshTokenFixture.REFRESH_TOKEN_VALUE;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RefreshTokenFinderTest {

	private @Mock RefreshTokenRepository refreshTokenRepository;
	private @InjectMocks RefreshTokenFinder refreshTokenFinder;

	@DisplayName("refreshTokenValue 에 해당하는 RefreshToken 객체를 반환한다")
	@Test
	void find() {
	    // given
		when(refreshTokenRepository.findOrNull(REFRESH_TOKEN_VALUE))
			.thenReturn(REFRESH_TOKEN);

	    // when
		RefreshToken refreshToken = refreshTokenFinder.find(REFRESH_TOKEN_VALUE);

		// then
		assertAll(
			() -> assertThat(refreshToken).isNotNull(),
			() -> assertThat(refreshToken.getValue()).isEqualTo(REFRESH_TOKEN_VALUE),
			() -> assertThat(refreshToken.getMemberNo()).isEqualTo(MEMBER_NO),
			() -> assertThat(refreshToken.getExpiresIn()).isEqualTo(EXPIRES_IN)
		);
	}

	@DisplayName("refreshTokenValue 에 해당하는 RefreshToken 를 찾지 못하면 예외를 발생시킨다")
	@Test
	void fail_find() {
		// given
		when(refreshTokenRepository.findOrNull(REFRESH_TOKEN_VALUE))
			.thenReturn(null);

		// when, then
		assertThatThrownBy(() -> refreshTokenFinder.find(REFRESH_TOKEN_VALUE))
			.isInstanceOf(UnAuthorizedException.class)
			.hasMessage(UnAuthorizedException.notFound(REFRESH_TOKEN_VALUE).getMessage());
	}
}
