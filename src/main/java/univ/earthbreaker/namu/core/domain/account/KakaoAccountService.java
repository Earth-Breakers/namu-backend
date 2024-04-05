package univ.earthbreaker.namu.core.domain.account;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import univ.earthbreaker.namu.event.EventPublisher;
import univ.earthbreaker.namu.event.character.InitCurrentCharacterEvent;
import univ.earthbreaker.namu.event.point.InitEnergyPointEvent;

@Service
public class KakaoAccountService implements AccountService {

	private final AccountCreateOrLoginManager accountCreateOrLoginManager;
	private final EventPublisher eventPublisher;

	public KakaoAccountService(AccountCreateOrLoginManager accountCreateOrLoginManager, EventPublisher eventPublisher) {
		this.accountCreateOrLoginManager = accountCreateOrLoginManager;
		this.eventPublisher = eventPublisher;
	}

	@Override
	@Transactional
	public LoginResult loginOrJoin(LoginCommand command) {
		LoginResult loginResult = accountCreateOrLoginManager.loginOrJoin(command);
		if (loginResult.isNewMember()) {
			eventPublisher.publish(new InitCurrentCharacterEvent(loginResult.memberNo()));
			eventPublisher.publish(new InitEnergyPointEvent(loginResult.memberNo()));
		}
		return loginResult;
	}
}
