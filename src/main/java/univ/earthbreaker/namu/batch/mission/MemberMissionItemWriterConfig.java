package univ.earthbreaker.namu.batch.mission;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import univ.earthbreaker.namu.database.core.mission.MemberMissionBatchRepository;

@Configuration
public class MemberMissionItemWriterConfig {

	@Bean
	@StepScope
	public MemberMissionItemWriter memberMissionItemWriter(
		MemberMissionBatchRepository memberMissionBatchRepository,
		ItemWriterStepExecutionListener itemWriterStepExecutionListener
	) {
		return new MemberMissionItemWriter(memberMissionBatchRepository, itemWriterStepExecutionListener);
	}

	@Bean
	public ItemWriterStepExecutionListener itemWriterStepExecutionListener() {
		return new ItemWriterStepExecutionListener();
	}
}
