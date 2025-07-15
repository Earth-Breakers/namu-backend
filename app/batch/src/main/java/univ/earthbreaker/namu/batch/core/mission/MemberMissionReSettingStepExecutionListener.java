package univ.earthbreaker.namu.batch.core.mission;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.batch.core.BatchException;
import univ.earthbreaker.namu.core.storage.mission.FixMissionJpaEntity;

@Component
@StepScope
public class MemberMissionReSettingStepExecutionListener extends AbstractExecutionContextManager<SharedFixMissions>
	implements StepExecutionListener {

	private SharedFixMissions fixMissions;

	public MemberMissionReSettingStepExecutionListener() {
		super();
	}

	@Override
	public void beforeStep(@NotNull StepExecution stepExecution) {
		super.setCurrentStepExecution(stepExecution);
		this.fixMissions = super.getDataFromJobExecutionContext(MISSIONS_PROMOTION_KEY);
	}

	public List<FixMissionJpaEntity> getFixMissions() {
		if (fixMissions == null) {
			throw BatchException.retrieveStepExecutionDataFailed();
		}
		return fixMissions.getJpaEntities();
	}
}
