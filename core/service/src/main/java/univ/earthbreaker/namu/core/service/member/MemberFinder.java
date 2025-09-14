package univ.earthbreaker.namu.core.service.member;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.member.Member;
import univ.earthbreaker.namu.core.domain.member.MemberNotFoundException;
import univ.earthbreaker.namu.core.domain.member.infra.MemberRepository;

@Component
public class MemberFinder {

	private final MemberRepository memberRepository;

	MemberFinder(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@NotNull Member find(long memberNo) {
		Member member = memberRepository.findMemberNoOrNull(memberNo);
		if (member != null) {
			return member;
		}
		throw MemberNotFoundException.notFound(memberNo);
	}
}
