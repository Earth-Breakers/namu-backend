package univ.earthbreaker.namu.core.service.member;

import org.springframework.stereotype.Service;

import univ.earthbreaker.namu.core.domain.member.Member;

@Service
public class MemberRetrieveService {

	private final MemberFinder memberFinder;

	public MemberRetrieveService(MemberFinder memberFinder) {
		this.memberFinder = memberFinder;
	}

	public Member retrieve(long memberNo) {
		return memberFinder.find(memberNo);
	}
}
