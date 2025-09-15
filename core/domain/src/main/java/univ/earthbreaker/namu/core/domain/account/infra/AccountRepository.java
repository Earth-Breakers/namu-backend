package univ.earthbreaker.namu.core.domain.account.infra;

import univ.earthbreaker.namu.core.domain.account.Account;
import univ.earthbreaker.namu.core.domain.account.AccountCreateCommand;

public interface AccountRepository {

	Account findOrNull(String socialId);

	Account create(AccountCreateCommand command);
}
