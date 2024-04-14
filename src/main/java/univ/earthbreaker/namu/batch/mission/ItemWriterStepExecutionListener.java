package univ.earthbreaker.namu.batch.mission;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

import univ.earthbreaker.namu.batch.BatchException;
import univ.earthbreaker.namu.database.core.mission.FixMissionJpaEntity;

public class ItemWriterStepExecutionListener extends AbstractStepExecutionManager<SharedFixMissions>
	implements StepExecutionListener {

	private SharedFixMissions fixMissions;

	@Override
	public void beforeStep(@NotNull StepExecution stepExecution) {
		super.setStepExecution(stepExecution);
		this.fixMissions = (SharedFixMissions)super.getData(MISSIONS_PROMOTION_KEY);
	}

	public List<FixMissionJpaEntity> getFixMissions() {
		if (fixMissions == null) {
			throw BatchException.retrieveStepExecutionDataFailed();
		}
		return fixMissions.getJpaEntities();
	}
}
