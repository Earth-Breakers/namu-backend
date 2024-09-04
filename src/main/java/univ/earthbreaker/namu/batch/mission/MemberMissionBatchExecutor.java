package univ.earthbreaker.namu.batch.mission;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MemberMissionBatchExecutor {

	private static final Logger LOG = LoggerFactory.getLogger(MemberMissionBatchExecutor.class);

	private final JobLauncher jobLauncher;
	private final Job memberMissionBatch;
	private final Clock koreaTimeClockInBatch;
	private final Integer chunkSize;

	public MemberMissionBatchExecutor(
		JobLauncher jobLauncher,
		Job memberMissionBatch,
		Clock koreaTimeClockInBatch,
		@Value("${chunkSize:10}") Integer chunkSize
	) {
		this.jobLauncher = jobLauncher;
		this.memberMissionBatch = memberMissionBatch;
		this.koreaTimeClockInBatch = koreaTimeClockInBatch;
		this.chunkSize = chunkSize;
	}

	public void execute() {
		LocalDate now = LocalDate.now(koreaTimeClockInBatch);
		JobParameters jobParameters = new JobParameters(Map.of(
			"batchDate", new JobParameter<>(now, LocalDate.class),
			"chunkSize", new JobParameter<>(chunkSize, Integer.class)
		));
		try {
			LOG.info("{} : 회원 미션 재설정 Batch 시작", LocalDateTime.now(koreaTimeClockInBatch));
			JobExecution jobExecution = jobLauncher.run(memberMissionBatch, jobParameters);
			LOG.info("{} : 회원 미션 재설정 Batch 완료 - {}", LocalDateTime.now(koreaTimeClockInBatch), jobExecution);
		} catch (Exception e) {
			LOG.error("{} : 회원 미션 재설정 Batch 실패 - {}", LocalDateTime.now(koreaTimeClockInBatch), e.getMessage());
		}
	}
}
