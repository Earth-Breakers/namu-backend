package univ.earthbreaker.namu.core.domain.member;

import org.springframework.stereotype.Service;

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
