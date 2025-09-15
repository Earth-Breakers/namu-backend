package univ.earthbreaker.namu.core.service.auth;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.auth.RefreshToken;
import univ.earthbreaker.namu.core.domain.auth.UnAuthorizedException;
import univ.earthbreaker.namu.core.domain.auth.infra.RefreshTokenRepository;

@Component
public class RefreshTokenFinder {

	private final RefreshTokenRepository refreshTokenRepository;

	public RefreshTokenFinder(RefreshTokenRepository refreshTokenRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
	}

	RefreshToken find(String refreshTokenValue) {
		RefreshToken refreshToken = refreshTokenRepository.findOrNull(refreshTokenValue);
		if (refreshToken == null) {
			throw UnAuthorizedException.notFound(refreshTokenValue);
		}
		return refreshToken;
	}
}
