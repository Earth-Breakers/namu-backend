package univ.earthbreaker.namu.core.domain.account;

import org.jetbrains.annotations.NotNull;

public interface TokenManager {

	@NotNull String createAccessToken(Object payload);

	@NotNull String createRefreshToken(Long memberNo);

	@NotNull String updateRefreshToken(Long memberNo);
}
