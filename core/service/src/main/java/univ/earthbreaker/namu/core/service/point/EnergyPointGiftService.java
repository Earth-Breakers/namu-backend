package univ.earthbreaker.namu.core.domain.point;

import static univ.earthbreaker.namu.core.domain.point.EnergyPointPushNotificationBridge.GiftResult;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class EnergyPointGiftService {

	private final EnergyPointManager energyPointManager;
	private final EnergyPointPushNotificationBridge energyPointPushNotificationBridge;

	public EnergyPointGiftService(
		EnergyPointManager energyPointManager,
		EnergyPointPushNotificationBridge energyPointPushNotificationBridge
	) {
		this.energyPointManager = energyPointManager;
		this.energyPointPushNotificationBridge = energyPointPushNotificationBridge;
	}

	public GiftResult giftEnergyPointToFriend(@NotNull EnergyGiftCommand command) {
		energyPointManager.transfer(command.getMemberNo(), command.getTargetMemberNo(), command.getPointValue());
		return energyPointPushNotificationBridge.find(command.getMemberNo(), command.getTargetMemberNo());
	}
}
