package univ.earthbreaker.namu.app.api.point;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.app.support.AuthMapping;
import univ.earthbreaker.namu.app.support.LoginMember;
import univ.earthbreaker.namu.core.domain.point.EnergyPointGiftService;
import univ.earthbreaker.namu.core.domain.point.EnergyPointPushNotificationBridge.GiftResult;
import univ.earthbreaker.namu.infra.notification.GiftPushNotificationSourceCommand;
import univ.earthbreaker.namu.infra.notification.NotificationPort;

@RestController
@RequestMapping("/v1/points")
public class EnergyPointGiftController {

	private final EnergyPointGiftService energyPointGiftService;
	private final NotificationPort notificationPort;

	public EnergyPointGiftController(EnergyPointGiftService energyPointGiftService, NotificationPort notificationPort) {
		this.energyPointGiftService = energyPointGiftService;
		this.notificationPort = notificationPort;
	}

	@AuthMapping
	@PostMapping("/gift/{targetMemberNo}")
	public ResponseEntity<Void> giveEnergyPointToFriend(
		@LoginMember Long memberNo,
		@PathVariable Long targetMemberNo,
		@RequestBody EnergyGiftRequest request
	) {
		GiftResult giftResult
			= energyPointGiftService.giftEnergyPointToFriend(request.toCommand(memberNo, targetMemberNo));
		notificationPort.sendAfterGift(new GiftPushNotificationSourceCommand(
			giftResult.memberNickname(),
			giftResult.targetTokenValue()
		));
		return ResponseEntity.noContent().build();
	}
}
