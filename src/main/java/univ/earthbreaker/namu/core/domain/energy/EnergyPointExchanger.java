package univ.earthbreaker.namu.core.domain.energy;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class EnergyPointExchanger {

	private final EnergyPointUpdater energyPointUpdater;

	EnergyPointExchanger(EnergyPointUpdater energyPointUpdater) {
		this.energyPointUpdater = energyPointUpdater;
	}

	@Transactional
	public void exchange(@NotNull Energy memberEnergy, @NotNull Energy friendEnergy, int pointValue) {
		memberEnergy.give(pointValue);
		friendEnergy.receive(pointValue);
		energyPointUpdater.update(memberEnergy);
		energyPointUpdater.update(friendEnergy);
	}
}
