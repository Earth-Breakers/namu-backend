package univ.earthbreaker.namu.core.service.auth;

import org.springframework.stereotype.Service;

import univ.earthbreaker.namu.core.domain.auth.RefreshToken;
import univ.earthbreaker.namu.core.domain.auth.TokenResult;

@Service
public class TokenReissueService {

	private final RefreshTokenFinder refreshTokenFinder;
	private final RefreshTokenValidator refreshTokenValidator;
	private final JwtManager jwtManager;

	public TokenReissueService(
		RefreshTokenFinder refreshTokenFinder,
		RefreshTokenValidator refreshTokenValidator,
		JwtManager jwtManager
	) {
		this.refreshTokenFinder = refreshTokenFinder;
		this.refreshTokenValidator = refreshTokenValidator;
		this.jwtManager = jwtManager;
	}

	public TokenResult reissue(String refreshTokenValue) {
		RefreshToken refreshToken = refreshTokenFinder.find(refreshTokenValue);
		refreshTokenValidator.validate(refreshToken);
		return new TokenResult(jwtManager.createAccessToken(refreshToken.getMemberNo()));
	}
}
