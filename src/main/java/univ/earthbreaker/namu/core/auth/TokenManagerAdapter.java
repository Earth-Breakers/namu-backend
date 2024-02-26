package univ.earthbreaker.namu.core.auth;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.account.TokenManager;

@Component
public class TokenManagerAdapter implements TokenManager {

	private final JwtManager jwtManager;
	private final RefreshTokenRepository refreshTokenRepository;

	public TokenManagerAdapter(JwtManager jwtManager, RefreshTokenRepository refreshTokenRepository) {
		this.jwtManager = jwtManager;
		this.refreshTokenRepository = refreshTokenRepository;
	}

	@Override
	public @NotNull String createAccessToken(Object payload) {
		return jwtManager.createAccessToken(payload);
	}

	@Override
	public @NotNull String createRefreshToken(Long memberNo) {
		RefreshToken refreshToken = jwtManager.createRefreshToken(memberNo);
		refreshTokenRepository.register(refreshToken);
		return refreshToken.getValue();
	}

	@Override
	public @NotNull String updateRefreshToken(Long memberNo) {
		RefreshToken refreshToken = jwtManager.createRefreshToken(memberNo);
		refreshTokenRepository.update(refreshToken, memberNo);
		return refreshToken.getValue();
	}
}
