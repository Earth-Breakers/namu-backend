package univ.earthbreaker.namu.core.domain.mission;

import java.util.concurrent.atomic.AtomicBoolean;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import univ.earthbreaker.namu.event.EventPublisher;
import univ.earthbreaker.namu.event.image.DeleteUploadedImageEvent;
import univ.earthbreaker.namu.event.point.AddRewardPointEvent;
import univ.earthbreaker.namu.event.post.PostCreateEvent;

@Service
public class MemberMissionCertifyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberMissionCertifyService.class);
	private static final AtomicBoolean IS_ROLLBACK = new AtomicBoolean(false);

	private final MemberMissionFinder memberMissionFinder;
	private final MissionCertifyHandler missionCertifyHandler;
	private final EventPublisher eventPublisher;
	private final TransactionTemplate transactionTemplate;

	public MemberMissionCertifyService(
		MemberMissionFinder memberMissionFinder,
		MissionCertifyHandler missionCertifyHandler,
		EventPublisher eventPublisher,
		PlatformTransactionManager platformTransactionManager
	) {
		this.memberMissionFinder = memberMissionFinder;
		this.missionCertifyHandler = missionCertifyHandler;
		this.eventPublisher = eventPublisher;
		this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
	}

	public void successMission(
		@NotNull MissionCompleteCommand missionCommand,
		@NotNull CertifiedMissionPostCommand postCommand
	) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(@NotNull TransactionStatus status) {
				try {
					MemberMission memberMission = memberMissionFinder.find(
						missionCommand.getMemberNo(), missionCommand.getMissionNo());
					MemberMission successMission = missionCertifyHandler.success(memberMission);
					publishRewardEventForSuccessMission(successMission);
					publishCreatePostEventForSuccessMission(postCommand, successMission);
				} catch (Exception e) {
					LOGGER.info("예외 발생으로 인한 트랜잭션 롤백 및 이미지 삭제 : {}", e.getMessage());
					status.setRollbackOnly();
					IS_ROLLBACK.set(status.isRollbackOnly());
				}
			}
		});
		publishDeleteUploadImageEventWhenTransactionRollback(postCommand.getImagePathKey());
	}

	private void publishDeleteUploadImageEventWhenTransactionRollback(@NotNull String imagePathKey) {
		if (IS_ROLLBACK.get()) {
			eventPublisher.publish(new DeleteUploadedImageEvent(imagePathKey));
		}
	}

	private void publishRewardEventForSuccessMission(@NotNull MemberMission successMission) {
		eventPublisher.publish(new AddRewardPointEvent(successMission.getMemberNo(), successMission.getRewardPoint()));
	}

	private void publishCreatePostEventForSuccessMission(
		@NotNull CertifiedMissionPostCommand postCommand,
		@NotNull MemberMission successMission
	) {
		eventPublisher.publish(
			new PostCreateEvent(
				postCommand.getMemberNo(),
				successMission.getActivity(),
				postCommand.getContent(),
				postCommand.getImagePathKey(),
				successMission.getNo())
		);
	}

	public void failureMission(@NotNull MissionCompleteCommand missionCommand) {
		MemberMission memberMission = memberMissionFinder.find(missionCommand.getMemberNo(), missionCommand.getMissionNo());
		missionCertifyHandler.failure(memberMission);
	}
}
