package univ.earthbreaker.namu.core.service.account;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import univ.earthbreaker.namu.core.domain.account.AccountService;
import univ.earthbreaker.namu.core.domain.account.AuthCommand;
import univ.earthbreaker.namu.core.domain.account.LoginCommand;
import univ.earthbreaker.namu.core.domain.account.LoginResult;
import univ.earthbreaker.namu.event.EventPublisher;
import univ.earthbreaker.namu.event.character.InitCurrentCharacterEvent;
import univ.earthbreaker.namu.event.point.InitEnergyPointEvent;

@Service
public class KakaoAccountService implements AccountService {

	// private final OAuthClientPort oAuthClientPort;
	private final AccountCreateOrLoginManager accountCreateOrLoginManager;
	private final EventPublisher eventPublisher;

	public KakaoAccountService(
		// OAuthClientPort oAuthClientPort,
		AccountCreateOrLoginManager accountCreateOrLoginManager,
		EventPublisher eventPublisher
	) {
		// this.oAuthClientPort = oAuthClientPort;
		this.accountCreateOrLoginManager = accountCreateOrLoginManager;
		this.eventPublisher = eventPublisher;
	}

	@Override
	@Transactional
	public LoginResult loginOrJoin(AuthCommand command) {
		// OAuthMemberInfoResult oAuthResult = oAuthClientPort.getOAuthMemberInfo(command.getSocialToken());
		LoginCommand loginCommand = command.toLoginCommand("oAuthResult.id()", "oAuthResult.nickname()");
		LoginResult loginResult = accountCreateOrLoginManager.loginOrJoin(loginCommand);
		if (loginResult.isNewMember()) {
			eventPublisher.publish(new InitCurrentCharacterEvent(loginResult.memberNo()));
			eventPublisher.publish(new InitEnergyPointEvent(loginResult.memberNo()));
		}
		return loginResult;
	}
}
