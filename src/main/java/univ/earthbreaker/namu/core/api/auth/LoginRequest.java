package univ.earthbreaker.namu.core.api.auth;

import univ.earthbreaker.namu.core.domain.account.LoginCommand;

public record LoginRequest(
	String socialToken,
	String notificationToken
) {
	public LoginCommand toCommand(String socialId, String socialNickname) {
		return LoginCommand.builder()
			.socialId(socialId)
			.socialNickname(socialNickname)
			.notificationToken(notificationToken)
			.build();
	}
}
