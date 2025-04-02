package univ.earthbreaker.namu.batch.core.mission;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MemberMissionBatchScheduler {

	private static final String FOUR_AM = "0 0 4 * * *";

	private final MemberMissionBatchExecutor memberMissionBatchExecutor;

	public MemberMissionBatchScheduler(MemberMissionBatchExecutor memberMissionBatchExecutor) {
		this.memberMissionBatchExecutor = memberMissionBatchExecutor;
	}

	@Scheduled(cron = FOUR_AM)
	public void executeMemberMissionBatch() {
		memberMissionBatchExecutor.execute();
	}
}
