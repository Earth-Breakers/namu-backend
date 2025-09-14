package univ.earthbreaker.namu.core.service.member;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.member.Member;
import univ.earthbreaker.namu.core.service.post.PostMemberBridge;

@Component
public class PostMemberBridgeAdapter implements PostMemberBridge {

	private final MemberFinder memberFinder;

	public PostMemberBridgeAdapter(MemberFinder memberFinder) {
		this.memberFinder = memberFinder;
	}

	@Override
	public PostMemberDto findMemberInfo(long memberNo) {
		Member member = memberFinder.find(memberNo);
		return new PostMemberDto(member.getNo(), member.getNickname());
	}
}
