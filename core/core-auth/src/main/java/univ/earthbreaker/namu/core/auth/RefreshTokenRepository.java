package univ.earthbreaker.namu.core.auth;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository {

	void register(@NotNull RefreshToken refreshToken);

	void update(@NotNull RefreshToken refreshToken, Long memberNo);

	@Nullable RefreshToken findOrNull(String refreshTokenValue);
}
