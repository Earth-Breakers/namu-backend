package univ.earthbreaker.namu.core.service.member;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.member.Member;
import univ.earthbreaker.namu.core.service.pushnotification.MemberBridge;
import univ.earthbreaker.namu.core.domain.pushnotification.MemberQuery;

@Component
public class MemberBridgeAdapter implements MemberBridge {

	private final MemberFinder memberFinder;

	public MemberBridgeAdapter(MemberFinder memberFinder) {
		this.memberFinder = memberFinder;
	}

	@Override
	public @NotNull MemberQuery findMember(long memberNo) {
		Member member = memberFinder.find(memberNo);
		return new MemberQuery(member.getNickname());
	}
}
