package univ.earthbreaker.namu.app.api.friend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.app.support.AuthMapping;
import univ.earthbreaker.namu.app.support.LoginMember;
import univ.earthbreaker.namu.core.domain.member.friend.FriendRelationCommand;
import univ.earthbreaker.namu.core.service.member.friend.FriendFollowService;

@RestController
@RequestMapping("/v1/friends")
public class FriendFollowController {

	private final FriendFollowService friendFollowService;

	public FriendFollowController(FriendFollowService friendFollowService) {
		this.friendFollowService = friendFollowService;
	}

	@AuthMapping
	@PostMapping("/follow/{targetMemberNo}")
	public ResponseEntity<Void> follow(@LoginMember Long memberNo, @PathVariable Long targetMemberNo) {
		friendFollowService.follow(new FriendRelationCommand(memberNo, targetMemberNo));
		return ResponseEntity.noContent().build();
	}
}
