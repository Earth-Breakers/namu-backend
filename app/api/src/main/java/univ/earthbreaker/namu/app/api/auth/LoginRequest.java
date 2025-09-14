package univ.earthbreaker.namu.app.api.auth;

import univ.earthbreaker.namu.core.domain.account.AuthCommand;

public record LoginRequest(
	String socialToken,
	String notificationToken
) {
	public AuthCommand toCommand() {
		return new AuthCommand(socialToken, notificationToken);
	}
}
