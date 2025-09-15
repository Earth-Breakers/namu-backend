package univ.earthbreaker.namu.app.api.pushnotification;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.app.support.AuthMapping;
import univ.earthbreaker.namu.app.support.LoginMember;
import univ.earthbreaker.namu.core.service.pushnotification.PushNotificationConstructService;

@RestController
@RequestMapping("/v1/push-notification")
public class PushNotificationController {

	private final PushNotificationConstructService pushNotificationConstructService;

	public PushNotificationController(PushNotificationConstructService pushNotificationConstructService) {
		this.pushNotificationConstructService = pushNotificationConstructService;
	}

	@AuthMapping
	@PostMapping("/all")
	public ResponseEntity<Void> pushNotification(@LoginMember Long memberNo) {
		pushNotificationConstructService.findAllMemberNotificationToken(memberNo);
		return ResponseEntity.ok().build();
	}

	@AuthMapping
	@PostMapping("/friends")
	public ResponseEntity<Void> pushNotification(
		@LoginMember Long memberNo,
		@RequestBody PushNotificationRequest request
	) {
		pushNotificationConstructService.findFriendsNotificationToken(memberNo, request.content());
		return ResponseEntity.ok().build();
	}
}
