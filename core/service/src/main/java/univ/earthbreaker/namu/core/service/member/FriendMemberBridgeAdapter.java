package univ.earthbreaker.namu.core.service.member;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.member.Member;
import univ.earthbreaker.namu.core.domain.member.MemberNotFoundException;
import univ.earthbreaker.namu.core.domain.member.infra.MemberRepository;
import univ.earthbreaker.namu.core.domain.member.friend.infra.FriendMemberBridge;

@Component
public class FriendMemberBridgeAdapter implements FriendMemberBridge {

	private final MemberRepository memberRepository;

	public FriendMemberBridgeAdapter(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public void checkExist(long targetMemberNo) {
		Member targetMember = memberRepository.findMemberNoOrNull(targetMemberNo);
		if (targetMember == null) {
			throw MemberNotFoundException.notFound(targetMemberNo);
		}
	}
}
