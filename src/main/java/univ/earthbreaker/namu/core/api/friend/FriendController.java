package univ.earthbreaker.namu.core.api.friend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.core.api.auth.support.AuthMapping;
import univ.earthbreaker.namu.core.api.auth.support.LoginMember;
import univ.earthbreaker.namu.core.domain.friend.FriendFollowService;
import univ.earthbreaker.namu.core.domain.friend.FriendRelationCommand;

@RestController
@RequestMapping("/v1/friend")
public class FriendController {

	private final FriendFollowService friendFollowService;

	public FriendController(FriendFollowService friendFollowService) {
		this.friendFollowService = friendFollowService;
	}

	@AuthMapping
	@PostMapping("/follow/{targetMemberNo}")
	public ResponseEntity<Void> follow(@LoginMember Long memberNo, @PathVariable Long targetMemberNo) {
		friendFollowService.register(new FriendRelationCommand(memberNo, targetMemberNo));
		return ResponseEntity.noContent().build();
	}
}
