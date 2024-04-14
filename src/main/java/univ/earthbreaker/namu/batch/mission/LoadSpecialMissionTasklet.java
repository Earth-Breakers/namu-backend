package univ.earthbreaker.namu.batch.mission;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.database.core.mission.FixMissionJpaEntity;
import univ.earthbreaker.namu.database.core.mission.FixMissionJpaRepository;

@Component
@StepScope
public class LoadSpecialMissionTasklet extends AbstractStepExecutionManager<SharedFixMissions> implements Tasklet {

	private final FixMissionJpaRepository fixMissionJpaRepository;

	public LoadSpecialMissionTasklet(FixMissionJpaRepository fixMissionJpaRepository) {
		this.fixMissionJpaRepository = fixMissionJpaRepository;
	}

	@Override
	public RepeatStatus execute(
		@NotNull StepContribution contribution,
		@NotNull ChunkContext chunkContext
	) {
		super.setStepExecution(chunkContext.getStepContext().getStepExecution());

		List<FixMissionJpaEntity> defaultMissions = fixMissionJpaRepository.findDefaultMissionsByRandom();
		List<FixMissionJpaEntity> todayMissions = fixMissionJpaRepository.findTodayMissionsByRandom();
		List<FixMissionJpaEntity> specialMissions = fixMissionJpaRepository.findSpecialMissions();

		List<FixMissionJpaEntity> fixMissionJpaEntities = new ArrayList<>();
		fixMissionJpaEntities.addAll(defaultMissions);
		fixMissionJpaEntities.addAll(todayMissions);
		fixMissionJpaEntities.addAll(specialMissions);

		super.putData(MISSIONS_PROMOTION_KEY, SharedFixMissions.from(fixMissionJpaEntities));

		return RepeatStatus.FINISHED;
	}
}
