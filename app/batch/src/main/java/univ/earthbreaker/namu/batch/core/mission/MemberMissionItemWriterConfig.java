package univ.earthbreaker.namu.batch.core.mission;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import univ.earthbreaker.namu.infra.storage.mission.MemberMissionBatchRepository;

@Configuration
public class MemberMissionItemWriterConfig {

	@Bean
	@StepScope
	public MemberMissionItemWriter memberMissionItemWriter(
		MemberMissionBatchRepository memberMissionBatchRepository,
		MemberMissionReSettingStepExecutionListener memberMissionReSettingStepExecutionListener
	) {
		return new MemberMissionItemWriter(memberMissionBatchRepository, memberMissionReSettingStepExecutionListener);
	}
}
