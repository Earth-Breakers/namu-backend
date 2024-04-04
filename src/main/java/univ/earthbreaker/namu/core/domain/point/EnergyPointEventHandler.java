package univ.earthbreaker.namu.core.domain.point;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import univ.earthbreaker.namu.event.point.AddRewardPointEvent;
import univ.earthbreaker.namu.event.point.InitEnergyPointEvent;

@Component
public class EnergyPointEventHandler {

	private final EnergyPointRepository energyPointRepository;

	public EnergyPointEventHandler(EnergyPointRepository energyPointRepository) {
		this.energyPointRepository = energyPointRepository;
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void giveRewardPoint(@NotNull AddRewardPointEvent event) {
		PointUpdateDbCommand command = new PointUpdateDbCommand(event.memberNo(), event.point());
		energyPointRepository.update(command);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void registerInitEnergyPoint(@NotNull InitEnergyPointEvent event) {
		energyPointRepository.register(event.memberNo());
	}
}
