package univ.earthbreaker.namu.database.core.account;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

import univ.earthbreaker.namu.core.domain.account.Account;
import univ.earthbreaker.namu.core.domain.account.AccountCreateCommand;
import univ.earthbreaker.namu.core.domain.account.AccountRepository;

@Repository
public class AccountJpaRepositoryAdapter implements AccountRepository {

	private final AccountJpaRepository accountJpaRepository;

	public AccountJpaRepositoryAdapter(AccountJpaRepository accountJpaRepository) {
		this.accountJpaRepository = accountJpaRepository;
	}

	@Override
	public @Nullable Account findOrNull(String socialId) {
		AccountJpaEntity accountJpaEntity = accountJpaRepository.findBySocialId(socialId);
		if (accountJpaEntity != null) {
			return accountJpaEntity.toAccount();
		}
		return null;
	}

	@Override
	public @NotNull Account create(AccountCreateCommand command) {
		return accountJpaRepository.save(
			new AccountJpaEntity(
				command.getSocialId(),
				command.getSocialType(),
				command.getMemberNo()
			)
		).toAccount();
	}
}
