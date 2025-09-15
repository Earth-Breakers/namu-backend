package univ.earthbreaker.namu.core.service.point;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import univ.earthbreaker.namu.core.domain.point.infra.PointUpdateDbCommand;
import univ.earthbreaker.namu.core.domain.point.infra.EnergyPointRepository;
import univ.earthbreaker.namu.event.point.AddRewardPointEvent;
import univ.earthbreaker.namu.event.point.InitEnergyPointEvent;

@Component
public class EnergyPointEventHandler {

	private final EnergyPointRepository energyPointRepository;

	public EnergyPointEventHandler(EnergyPointRepository energyPointRepository) {
		this.energyPointRepository = energyPointRepository;
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void giveRewardPoint(AddRewardPointEvent event) {
		PointUpdateDbCommand command = new PointUpdateDbCommand(event.memberNo(), event.point());
		energyPointRepository.receivePoint(command);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void registerInitEnergyPoint(InitEnergyPointEvent event) {
		energyPointRepository.register(event.memberNo());
	}
}
