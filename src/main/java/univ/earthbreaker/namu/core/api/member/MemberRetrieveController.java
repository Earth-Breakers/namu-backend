package univ.earthbreaker.namu.core.api.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.core.api.auth.support.AuthMapping;
import univ.earthbreaker.namu.core.api.auth.support.LoginMember;
import univ.earthbreaker.namu.core.domain.member.Member;
import univ.earthbreaker.namu.core.domain.member.MemberRetrieveService;

@RestController
@AuthMapping
@RequestMapping("/v1/members")
public class MemberRetrieveController {

	private final MemberRetrieveService memberRetrieveService;

	public MemberRetrieveController(MemberRetrieveService memberRetrieveService) {
		this.memberRetrieveService = memberRetrieveService;
	}

	@GetMapping("/home")
	public ResponseEntity<HomeMemberResponse> retrieve(@LoginMember Long memberNo) {
		Member member = memberRetrieveService.retrieve(memberNo);
		return ResponseEntity.ok(HomeMemberResponse.from(member));
	}
}
