package univ.earthbreaker.namu.core.domain.account;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public interface AccountMemberCreator {

	@NotNull Long create(String socialNickname);
}
