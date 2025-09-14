package univ.earthbreaker.namu.core.domain.account;

import jakarta.validation.constraints.NotBlank;
import univ.earthbreaker.namu.core.domain.common.SelfValidating;

public class AuthCommand extends SelfValidating<AuthCommand> {

	private final @NotBlank String socialToken;
	private final @NotBlank String notificationToken;

	public AuthCommand(String socialToken, String notificationToken) {
		this.socialToken = socialToken;
		this.notificationToken = notificationToken;
		this.validateSelf("socialToken 과 notificationToken 은 공백이 될 수 없습니다");
	}

	public LoginCommand toLoginCommand(String socialId, String nickname) {
		return LoginCommand.builder()
			.socialId(socialId)
			.socialNickname(nickname)
			.notificationToken(notificationToken)
			.build();
	}

	public String getSocialToken() {
		return socialToken;
	}

	public String getNotificationToken() {
		return notificationToken;
	}
}
