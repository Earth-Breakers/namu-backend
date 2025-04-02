package univ.earthbreaker.namu.core.domain.account;

public interface AccountService {
	LoginResult loginOrJoin(LoginCommand command);
}
