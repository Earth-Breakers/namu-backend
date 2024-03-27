package univ.earthbreaker.namu.core.domain.point;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import univ.earthbreaker.namu.event.point.AddRewardPointEvent;

@Component
public class AddRewardPointEventHandler {

	private final EnergyPointRepository energyPointRepository;

	public AddRewardPointEventHandler(EnergyPointRepository energyPointRepository) {
		this.energyPointRepository = energyPointRepository;
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void giveRewardPoint(@NotNull AddRewardPointEvent event) {
		PointUpdateDbCommand command = new PointUpdateDbCommand(event.memberNo(), event.point());
		energyPointRepository.update(command);
	}
}
