package univ.earthbreaker.namu.core.api.pushnotification;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.core.api.auth.support.LoginMember;
import univ.earthbreaker.namu.core.domain.pushnotification.PushNotificationConstructService;
import univ.earthbreaker.namu.core.domain.pushnotification.PushNotificationConstructResult;
import univ.earthbreaker.namu.external.notification.NotificationPort;
import univ.earthbreaker.namu.external.notification.PushNotificationSourceCommand;

@RestController
@RequestMapping("/v1/push-notification")
public class PushNotificationController {

	private final PushNotificationConstructService pushNotificationConstructService;
	private final NotificationPort notificationPort;

	public PushNotificationController(
		PushNotificationConstructService pushNotificationConstructService,
		NotificationPort notificationPort
	) {
		this.pushNotificationConstructService = pushNotificationConstructService;
		this.notificationPort = notificationPort;
	}

	@PostMapping("/all")
	public ResponseEntity<Void> pushNotification(@LoginMember Long memberNo) {
		PushNotificationConstructResult result = pushNotificationConstructService.findAllMemberNotificationToken(memberNo);
		notificationPort.sendShowOffMessage(
			new PushNotificationSourceCommand(
				result.nickname(),
				result.characterName(),
				null,
				result.notificationTokens())
		);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/friends")
	public ResponseEntity<Void> pushNotification(
		@LoginMember Long memberNo,
		@RequestBody PushNotificationRequest request
	) {
		PushNotificationConstructResult result = pushNotificationConstructService.findFriendsNotificationToken(memberNo);
		notificationPort.sendShowOffMessage(
			new PushNotificationSourceCommand(
				result.nickname(),
				result.characterName(),
				request.content(),
				result.notificationTokens())
		);
		return ResponseEntity.ok().build();
	}
}
