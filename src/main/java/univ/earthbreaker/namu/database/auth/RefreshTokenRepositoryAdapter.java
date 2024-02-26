package univ.earthbreaker.namu.database.auth;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

import univ.earthbreaker.namu.core.auth.RefreshToken;
import univ.earthbreaker.namu.core.auth.RefreshTokenRepository;

@Repository
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepository {

	private final RefreshTokenJpaRepository refreshTokenJpaRepository;

	public RefreshTokenRepositoryAdapter(RefreshTokenJpaRepository refreshTokenJpaRepository) {
		this.refreshTokenJpaRepository = refreshTokenJpaRepository;
	}

	@Override
	public void register(@NotNull RefreshToken refreshToken) {
		refreshTokenJpaRepository.save(new RefreshTokenJpaEntity(
			refreshToken.getValue(),
			refreshToken.getExpiresIn(),
			refreshToken.getMemberNo()
		));
	}

	@Override
	public void update(@NotNull RefreshToken refreshToken, Long memberNo) {
		refreshTokenJpaRepository.updateTokenValueAndExpiresIn(
			refreshToken.getValue(),
			refreshToken.getExpiresIn(),
			memberNo
		);
	}

	@Override
	public @Nullable RefreshToken findOrNull(String refreshTokenValue) {
		RefreshTokenJpaEntity refreshTokenJpaEntity = refreshTokenJpaRepository.findByToken(refreshTokenValue);
		if (refreshTokenJpaEntity != null) {
			return refreshTokenJpaEntity.toRefreshToken();
		}
		return null;
	}
}
