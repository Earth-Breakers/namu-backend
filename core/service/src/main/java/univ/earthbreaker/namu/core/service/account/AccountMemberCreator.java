package univ.earthbreaker.namu.core.service.account;

import org.springframework.stereotype.Component;

@Component
public interface AccountMemberCreator {
	Long create(String socialNickname);
}
