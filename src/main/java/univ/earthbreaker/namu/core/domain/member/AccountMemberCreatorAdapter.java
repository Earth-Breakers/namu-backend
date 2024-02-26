package univ.earthbreaker.namu.core.domain.member;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.account.AccountMemberCreator;

@Component
public class AccountMemberCreatorAdapter implements AccountMemberCreator {

	private final MemberRepository memberRepository;

	public AccountMemberCreatorAdapter(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public @NotNull Long create(String socialNickname) {
		return memberRepository.create(socialNickname);
	}
}
