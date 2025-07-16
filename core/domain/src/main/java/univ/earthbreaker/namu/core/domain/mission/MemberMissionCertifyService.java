package univ.earthbreaker.namu.core.domain.mission;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import univ.earthbreaker.namu.core.support.TransactionHandler;
import univ.earthbreaker.namu.event.EventPublisher;
import univ.earthbreaker.namu.event.point.AddRewardPointEvent;
import univ.earthbreaker.namu.event.post.PostCreateEvent;

@Service
public class MemberMissionCertifyService {

	private final MemberMissionFinder memberMissionFinder;
	private final MissionCertifyHandler missionCertifyHandler;
	private final EventPublisher eventPublisher;
	private final TransactionHandler transactionHandler;

	public MemberMissionCertifyService(
		MemberMissionFinder memberMissionFinder,
		MissionCertifyHandler missionCertifyHandler,
		EventPublisher eventPublisher,
		TransactionHandler transactionHandler
	) {
		this.memberMissionFinder = memberMissionFinder;
		this.missionCertifyHandler = missionCertifyHandler;
		this.eventPublisher = eventPublisher;
		this.transactionHandler = transactionHandler;
	}

	public void successMission(
		@NotNull MissionCompleteCommand mc,
		@NotNull CertifiedMissionPostCommand pc
	) {
		transactionHandler.execute(() -> {
			MemberMission memberMission = memberMissionFinder.find(mc.getMemberNo(), mc.getMissionNo());
			MemberMission successMission = missionCertifyHandler.success(memberMission);
			publishRewardEventForSuccessMission(successMission); // 리워드 포인트 지급 이벤트 발행
			publishCreatePostEventForSuccessMission(pc, successMission); // 게시글 생성 이벤트 발행
			return null;
		});
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
