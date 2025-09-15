package univ.earthbreaker.namu.core.service.auth;

import java.time.Clock;
import java.time.LocalDateTime;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.auth.RefreshToken;
import univ.earthbreaker.namu.core.domain.auth.UnAuthorizedException;

@Component
public class RefreshTokenValidator {

	private final Clock koreaTimeClock;

	public RefreshTokenValidator(Clock koreaTimeClock) {
		this.koreaTimeClock = koreaTimeClock;
	}

	void validate(@NotNull RefreshToken refreshToken) {
		if (refreshToken.isExpired(LocalDateTime.now(koreaTimeClock))) {
			throw UnAuthorizedException.expired(refreshToken.getValue());
		}
	}
}
