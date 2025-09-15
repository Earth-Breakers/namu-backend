package univ.earthbreaker.namu.core.service.point;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import univ.earthbreaker.namu.core.domain.point.ProvideEnergyPointCommand;
import univ.earthbreaker.namu.event.EventPublisher;
import univ.earthbreaker.namu.event.character.AddEnergyPointEvent;

@Service
public class EnergyPointProvideService {

	private final EnergyPointManager energyPointManager;
	private final EventPublisher eventPublisher;

	public EnergyPointProvideService(EnergyPointManager energyPointManager, EventPublisher eventPublisher) {
		this.energyPointManager = energyPointManager;
		this.eventPublisher = eventPublisher;
	}

	@Transactional
	public void provideEnergyToCharacter(@NotNull ProvideEnergyPointCommand command) {
		energyPointManager.useEnergyPoint(command.getMemberNo(), command.getPoint());
		eventPublisher.publish(new AddEnergyPointEvent(
			command.getMemberNo(),
			command.getPoint(),
			command.getEnergyType()
		));
	}
}
