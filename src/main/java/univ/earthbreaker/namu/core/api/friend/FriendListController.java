package univ.earthbreaker.namu.core.api.friend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.core.api.auth.support.AuthMapping;
import univ.earthbreaker.namu.core.api.auth.support.LoginMember;
import univ.earthbreaker.namu.core.domain.member.friend.FriendFinderService;

@RestController
@RequestMapping("/v1/friends")
public class FriendListController {

	private final FriendFinderService friendFinderService;

	public FriendListController(FriendFinderService friendFinderService) {
		this.friendFinderService = friendFinderService;
	}

	@AuthMapping
	@GetMapping("/following")
	public ResponseEntity<FriendListResponse> retrieveFriendList(@LoginMember Long memberNo) {
		FriendListResponse friendListResponse = FriendListResponse.from(friendFinderService.findMyFriendList(memberNo));
		return ResponseEntity.ok(friendListResponse);
	}
}
