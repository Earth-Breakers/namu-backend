package univ.earthbreaker.namu.core.domain.account;

public interface AccountService {
	LoginResult loginOrJoin(AuthCommand command);
}
