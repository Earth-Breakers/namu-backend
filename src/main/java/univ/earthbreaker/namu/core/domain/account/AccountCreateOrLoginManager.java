package univ.earthbreaker.namu.core.domain.account;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AccountCreateOrLoginManager {

	private final AccountRepository accountRepository;
	private final AccountMemberCreator accountMemberCreator;
	private final AccountPushNotificationManager accountPushNotificationManager;
	private final TokenManager tokenManager;

	public AccountCreateOrLoginManager(
		AccountRepository accountRepository,
		AccountMemberCreator accountMemberCreator,
		AccountPushNotificationManager accountPushNotificationManager,
		TokenManager tokenManager
	) {
		this.accountRepository = accountRepository;
		this.accountMemberCreator = accountMemberCreator;
		this.accountPushNotificationManager = accountPushNotificationManager;
		this.tokenManager = tokenManager;
	}

	@Transactional
	public LoginResult loginOrJoin(@NotNull LoginCommand command) {
		Account account = accountRepository.findOrNull(command.getSocialId());
		if (account == null) {
			return join(command);
		} else {
			return login(command.getNotificationToken(), account.getMemberNo());
		}
	}

	private @NotNull LoginResult join(@NotNull LoginCommand command) {
		Long memberNo = accountMemberCreator.create(command.getSocialNickname());
		accountRepository.create(command.toKakaoCommand(memberNo));
		accountPushNotificationManager.register(memberNo, command.getNotificationToken());

		String accessTokenValue = tokenManager.createAccessToken(memberNo);
		String refreshTokenValue = tokenManager.createRefreshToken(memberNo);

		return new LoginResult(accessTokenValue, refreshTokenValue, true);
	}

	private @NotNull LoginResult login(String pushNotificationToken, Long memberNo) {
		accountPushNotificationManager.updateIfTokenModified(memberNo, pushNotificationToken);
		String accessTokenValue = tokenManager.createAccessToken(memberNo);
		String updatedRefreshTokenValue = tokenManager.updateRefreshToken(memberNo);
		return new LoginResult(accessTokenValue, updatedRefreshTokenValue, false);
	}
}
