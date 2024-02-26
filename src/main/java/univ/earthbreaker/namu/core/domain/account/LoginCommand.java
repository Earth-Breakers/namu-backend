package univ.earthbreaker.namu.core.domain.account;

import javax.validation.constraints.NotBlank;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.common.SelfValidating;

public class LoginCommand extends SelfValidating<LoginCommand> {

	private final @NotBlank String socialId;
	private final @NotBlank String socialNickname;
	private final @NotBlank String notificationToken;

	private LoginCommand(String socialId, String socialNickname, String notificationToken) {
		this.socialId = socialId;
		this.socialNickname = socialNickname;
		this.notificationToken = notificationToken;
		this.validateSelf("socialId, socialNickname 과 notificationToken 은 공백이 될 수 없습니다");
	}

	public KakaoAccountCreateCommand toKakaoCommand(Long memberNo) {
		return new KakaoAccountCreateCommand(socialId, memberNo);
	}

	public static @NotNull LoginCommandBuilder builder() {
		return new LoginCommandBuilder();
	}

	public static class LoginCommandBuilder {
		private String socialId;
		private String socialNickname;
		private String notificationToken;

		public LoginCommandBuilder socialId(String socialId) {
			this.socialId = socialId;
			return this;
		}

		public LoginCommandBuilder socialNickname(String socialNickname) {
			this.socialNickname = socialNickname;
			return this;
		}

		public LoginCommandBuilder notificationToken(String notificationToken) {
			this.notificationToken = notificationToken;
			return this;
		}

		public LoginCommand build() {
			return new LoginCommand(this.socialId, this.socialNickname, this.notificationToken);
		}
	}

	public String getSocialId() {
		return socialId;
	}

	public String getSocialNickname() {
		return socialNickname;
	}

	public String getNotificationToken() {
		return notificationToken;
	}
}
