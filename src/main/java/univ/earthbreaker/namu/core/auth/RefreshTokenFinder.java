package univ.earthbreaker.namu.core.auth;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenFinder {

	private final RefreshTokenRepository refreshTokenRepository;

	public RefreshTokenFinder(RefreshTokenRepository refreshTokenRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
	}

	@NotNull RefreshToken find(String refreshTokenValue) {
		RefreshToken refreshToken = refreshTokenRepository.findOrNull(refreshTokenValue);
		if (refreshToken == null) {
			throw UnAuthorizedException.notFound(refreshTokenValue);
		}
		return refreshToken;
	}
}
