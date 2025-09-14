package univ.earthbreaker.namu.core.service.auth;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import univ.earthbreaker.namu.core.domain.auth.RefreshToken;
import univ.earthbreaker.namu.core.domain.auth.infra.RefreshTokenRepository;
import univ.earthbreaker.namu.core.service.account.TokenManager;

@Component
public class TokenManagerAdapter implements TokenManager {

	private final JwtManager jwtManager;
	private final RefreshTokenRepository refreshTokenRepository;

	public TokenManagerAdapter(JwtManager jwtManager, RefreshTokenRepository refreshTokenRepository) {
		this.jwtManager = jwtManager;
		this.refreshTokenRepository = refreshTokenRepository;
	}

	@Override
	public String createAccessToken(Object payload) {
		return jwtManager.createAccessToken(payload);
	}

	@Override
	public String createRefreshToken(Long memberNo) {
		RefreshToken refreshToken = jwtManager.createRefreshToken(memberNo);
		refreshTokenRepository.register(refreshToken);
		return refreshToken.getValue();
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public String updateRefreshToken(Long memberNo) {
		RefreshToken refreshToken = jwtManager.createRefreshToken(memberNo);
		refreshTokenRepository.update(refreshToken, memberNo);
		return refreshToken.getValue();
	}
}
