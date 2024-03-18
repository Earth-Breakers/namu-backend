package univ.earthbreaker.namu.core.domain.energy;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class EnergyGiftService {

	private final EnergyPointFinder energyPointFinder;
	private final EnergyPointExchanger energyPointExchanger;

	public EnergyGiftService(EnergyPointFinder energyPointFinder, EnergyPointExchanger energyPointExchanger) {
		this.energyPointFinder = energyPointFinder;
		this.energyPointExchanger = energyPointExchanger;
	}

	public void givePointToFriend(@NotNull EnergyGiftCommand command) {
		Energy myEnergy = energyPointFinder.find(command.memberNo());
		Energy friendEnergy = energyPointFinder.find(command.targetMemberNo());
		energyPointExchanger.exchange(myEnergy, friendEnergy, command.pointValue());
	}
}
