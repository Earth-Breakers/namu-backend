package univ.earthbreaker.namu.core.domain.account;

import org.springframework.stereotype.Service;

@Service
public class KakaoAccountService implements AccountService {

	private final AccountCreateOrLoginManager accountCreateOrLoginManager;

	public KakaoAccountService(AccountCreateOrLoginManager accountCreateOrLoginManager) {
		this.accountCreateOrLoginManager = accountCreateOrLoginManager;
	}

	@Override
	public LoginResult loginOrJoin(LoginCommand command) {
		return accountCreateOrLoginManager.loginOrJoin(command);
	}
}
