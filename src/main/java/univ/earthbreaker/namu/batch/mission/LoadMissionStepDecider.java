package univ.earthbreaker.namu.batch.mission;

import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

@Component
public class LoadMissionStepDecider implements JobExecutionDecider {

	static final String SPECIAL_STEP = "SPECIAL_STEP";
	static final String NORMAL_STEP = "NORMAL_STEP";

	private final MissionDateSupport missionDateSupport;

	public LoadMissionStepDecider(MissionDateSupport missionDateSupport) {
		this.missionDateSupport = missionDateSupport;
	}

	@Override
	public @NotNull FlowExecutionStatus decide(
		@NotNull JobExecution jobExecution,
		StepExecution stepExecution
	) {
		if (missionDateSupport.isSpecialDate()) {
			return new FlowExecutionStatus(SPECIAL_STEP);
		} else {
			return new FlowExecutionStatus(NORMAL_STEP);
		}
	}
}
