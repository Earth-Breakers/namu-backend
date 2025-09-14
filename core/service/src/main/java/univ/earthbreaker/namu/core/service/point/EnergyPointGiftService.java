package univ.earthbreaker.namu.core.service.point;

import static univ.earthbreaker.namu.core.domain.point.infra.EnergyPointNotificationPort.GiftPushNotificationSourceCommand;
import static univ.earthbreaker.namu.core.service.point.EnergyPointPushNotificationBridge.GiftResult;

import org.springframework.stereotype.Service;

import univ.earthbreaker.namu.core.domain.point.EnergyGiftCommand;
import univ.earthbreaker.namu.core.domain.point.infra.EnergyPointNotificationPort;

@Service
public class EnergyPointGiftService {

	private final EnergyPointManager energyPointManager;
	private final EnergyPointPushNotificationBridge energyPointPushNotificationBridge;
	private final EnergyPointNotificationPort notificationPort;

	public EnergyPointGiftService(
		EnergyPointManager energyPointManager,
		EnergyPointPushNotificationBridge energyPointPushNotificationBridge,
		EnergyPointNotificationPort notificationPort
	) {
		this.energyPointManager = energyPointManager;
		this.energyPointPushNotificationBridge = energyPointPushNotificationBridge;
		this.notificationPort = notificationPort;
	}

	public void giftEnergyPointToFriend(EnergyGiftCommand command) {
		energyPointManager.transfer(command.getMemberNo(), command.getTargetMemberNo(), command.getPointValue());
		GiftResult result = energyPointPushNotificationBridge.find(command.getMemberNo(), command.getTargetMemberNo());
		notificationPort.sendAfterGift(new GiftPushNotificationSourceCommand(
			result.memberNickname(),
			result.targetTokenValue()
		));
	}
}
