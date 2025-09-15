package univ.earthbreaker.namu.core.domain.auth.infra;

import univ.earthbreaker.namu.core.domain.auth.RefreshToken;

public interface RefreshTokenRepository {

	void register(RefreshToken refreshToken);

	void update(RefreshToken refreshToken, Long memberNo);

	RefreshToken findOrNull(String refreshTokenValue);
}
