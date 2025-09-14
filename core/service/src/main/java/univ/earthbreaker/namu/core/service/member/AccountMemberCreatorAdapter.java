package univ.earthbreaker.namu.core.service.member;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.service.account.AccountMemberCreator;
import univ.earthbreaker.namu.core.domain.member.infra.MemberRepository;

@Component
public class AccountMemberCreatorAdapter implements AccountMemberCreator {

	private final MemberRepository memberRepository;

	public AccountMemberCreatorAdapter(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public Long create(String socialNickname) {
		return memberRepository.create(socialNickname);
	}
}
