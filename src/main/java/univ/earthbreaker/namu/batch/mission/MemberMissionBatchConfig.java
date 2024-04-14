package univ.earthbreaker.namu.batch.mission;

import java.time.LocalDate;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class MemberMissionBatchConfig {

	private final JobRepository jobRepository;
	private final JobExecutionDecider jobExecutionDecider;

	public MemberMissionBatchConfig(
		JobRepository jobRepository,
		JobExecutionDecider jobExecutionDecider
	) {
		this.jobRepository = jobRepository;
		this.jobExecutionDecider = jobExecutionDecider;
	}

	@Bean("memberMissionBatch")
	public Job memberMissionBatch() {
		return new JobBuilder("memberMissionBatch", jobRepository)
			.start(jobExecutionDecider)
			.from(jobExecutionDecider)
				.on(LoadMissionStepDecider.NORMAL_STEP)
				.to(normalStep(null, null))
				.next(memberMissionReSettingBatchStep(null, null, null, null, null))
			.from(jobExecutionDecider)
				.on(LoadMissionStepDecider.SPECIAL_STEP)
				.to(specialStep(null, null))
				.next(memberMissionReSettingBatchStep(null, null, null, null, null))
			.end()
			.build();
	}

	@Bean("normalStep")
	@JobScope
	public Step normalStep(
		@Qualifier("loadNormalMissionTasklet") Tasklet loadNormalMissionTasklet,
		PlatformTransactionManager transactionManager
	) {
		return new StepBuilder("normalStep", jobRepository)
			.tasklet(loadNormalMissionTasklet, transactionManager)
			.listener(contextPromotionListener())
			.build();
	}

	@Bean("specialStep")
	@JobScope
	public Step specialStep(
		@Qualifier("loadSpecialMissionTasklet") Tasklet loadSpecialMissionTasklet,
		PlatformTransactionManager transactionManager
	) {
		return new StepBuilder("specialStep", jobRepository)
			.tasklet(loadSpecialMissionTasklet, transactionManager)
			.listener(contextPromotionListener())
			.build();
	}

	@Bean("memberMissionReSettingBatchStep")
	@JobScope
	public Step memberMissionReSettingBatchStep(
		@Value("#{jobParameters[chunkSize]}") Integer chunkSize,
		JdbcPagingItemReader<MemberBatchEntity> memberItemReader,
		MemberMissionItemWriter memberMissionItemWriter,
		ItemWriterStepExecutionListener writerStepExecutionListener,
		PlatformTransactionManager transactionManager
	) {
		return new StepBuilder("memberMissionReSettingBatchStep", jobRepository)
			.<MemberBatchEntity, MemberBatchEntity>chunk(chunkSize, transactionManager)
			.reader(memberItemReader)
			.writer(memberMissionItemWriter)
			.listener(writerStepExecutionListener)
			.build();
	}

	@Bean
	public ExecutionContextPromotionListener contextPromotionListener() {
		ExecutionContextPromotionListener promotionListener = new ExecutionContextPromotionListener();
		promotionListener.setKeys(new String[] {AbstractStepExecutionManager.MISSIONS_PROMOTION_KEY});
		return promotionListener;
	}

	@Bean
	@JobScope
	public MissionDateSupport missionDateSupport(
		@Value("#{jobParameters[batchDate]}") LocalDate batchDate
	) {
		return new MissionDateSupport(batchDate);
	}
}
