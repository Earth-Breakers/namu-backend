package univ.earthbreaker.namu.core.domain.account;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository {

	@Nullable Account findOrNull(String socialId);

	@NotNull Account create(AccountCreateCommand command);
}
