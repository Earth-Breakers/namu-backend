package univ.earthbreaker.namu.core.api.friend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.core.support.AuthMapping;
import univ.earthbreaker.namu.core.support.LoginMember;
import univ.earthbreaker.namu.core.domain.member.friend.FollowFriendPushNotificationBridge.FollowResult;
import univ.earthbreaker.namu.core.domain.member.friend.FriendFollowService;
import univ.earthbreaker.namu.core.domain.member.friend.FriendRelationCommand;
import univ.earthbreaker.namu.external.notification.FollowPushNotificationSourceCommand;
import univ.earthbreaker.namu.external.notification.NotificationPort;

@RestController
@RequestMapping("/v1/friends")
public class FriendFollowController {

	private final FriendFollowService friendFollowService;
	private final NotificationPort notificationPort;

	public FriendFollowController(FriendFollowService friendFollowService, NotificationPort notificationPort) {
		this.friendFollowService = friendFollowService;
		this.notificationPort = notificationPort;
	}

	@AuthMapping
	@PostMapping("/follow/{targetMemberNo}")
	public ResponseEntity<Void> follow(@LoginMember Long memberNo, @PathVariable Long targetMemberNo) {
		FollowResult followResult = friendFollowService.follow(new FriendRelationCommand(memberNo, targetMemberNo));
		notificationPort.sendAfterFollow(new FollowPushNotificationSourceCommand(
			followResult.memberNickname(),
			followResult.targetNickname(),
			followResult.targetTokenValue()
		));
		return ResponseEntity.noContent().build();
	}
}
