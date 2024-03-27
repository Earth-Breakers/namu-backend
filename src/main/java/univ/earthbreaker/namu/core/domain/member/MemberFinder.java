package univ.earthbreaker.namu.core.domain.member;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

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
