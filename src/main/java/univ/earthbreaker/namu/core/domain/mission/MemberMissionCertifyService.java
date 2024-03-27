package univ.earthbreaker.namu.core.domain.mission;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import univ.earthbreaker.namu.event.point.AddRewardPointEvent;
import univ.earthbreaker.namu.event.image.DeleteUploadedImageEvent;
import univ.earthbreaker.namu.event.EventPublisher;
import univ.earthbreaker.namu.event.post.PostCreateEvent;

@Service
public class MemberMissionCertifyService {

	private final MemberMissionFinder memberMissionFinder;
	private final MissionCertifyHandler missionCertifyHandler;
	private final EventPublisher eventPublisher;

	public MemberMissionCertifyService(
		MemberMissionFinder memberMissionFinder,
		MissionCertifyHandler missionCertifyHandler,
		EventPublisher eventPublisher
	) {
		this.memberMissionFinder = memberMissionFinder;
		this.missionCertifyHandler = missionCertifyHandler;
		this.eventPublisher = eventPublisher;
	}

	@Transactional
	public void successMission(
		@NotNull MissionCompleteCommand missionCommand,
		@NotNull CertifiedMissionPostCommand postCommand
	) {
		MemberMission memberMission = memberMissionFinder.find(missionCommand.getMemberNo(), missionCommand.getMissionNo());
		MemberMission successMission = missionCertifyHandler.success(memberMission);
		publishRewardEventForSuccessMission(successMission);
		publishCreatePostEventForSuccessMission(postCommand, successMission);
		publishDeleteUploadImageEventWhenTransactionRollback(postCommand.getImagePathKey());
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
				successMission.getTitle(),
				postCommand.getContent(),
				postCommand.getImagePathKey(),
				successMission.getNo())
		);
	}

	private void publishDeleteUploadImageEventWhenTransactionRollback(@NotNull String imagePathKey) {
		eventPublisher.publish(new DeleteUploadedImageEvent(imagePathKey));
	}

	public void failureMission(@NotNull MissionCompleteCommand missionCommand) {
		MemberMission memberMission = memberMissionFinder.find(missionCommand.getMemberNo(), missionCommand.getMissionNo());
		missionCertifyHandler.failure(memberMission);
	}
}
