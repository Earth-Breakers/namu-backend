package univ.earthbreaker.namu.core.domain.point;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class EnergyPointGiftService {

	private final EnergyPointManager energyPointManager;

	public EnergyPointGiftService(EnergyPointManager energyPointManager) {
		this.energyPointManager = energyPointManager;
	}

	public void giftEnergyPointToFriend(@NotNull EnergyGiftCommand command) {
		energyPointManager.transfer(command.getMemberNo(), command.getTargetMemberNo(), command.getPointValue());
	}
}
