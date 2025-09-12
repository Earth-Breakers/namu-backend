package univ.earthbreaker.namu.core.domain.mission;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import univ.earthbreaker.namu.core.support.tx.TransactionHandler;
import univ.earthbreaker.namu.core.support.retry.RetryHandler;
import univ.earthbreaker.namu.event.EventPublisher;
import univ.earthbreaker.namu.event.point.AddRewardPointEvent;
import univ.earthbreaker.namu.event.post.PostCreateEvent;

@Service
public class MemberMissionCertifyService {

	private final ImageManager imageManager;
	private final PointManager pointManager;
	private final KafkaTemplate<String, RetryMessage> kafkaTemplate;
	private final MissionCertifyTrackingService missionCertifyTrackingService;
	private final String missionRetryTopic;

	private final MemberMissionFinder memberMissionFinder;
	private final MissionCertifyHandler missionCertifyHandler;
	private final EventPublisher eventPublisher;
	private final TransactionHandler transactionHandler;
	private final RetryHandler retryHandler;

	public MemberMissionCertifyService(
		MemberMissionFinder memberMissionFinder,
		MissionCertifyHandler missionCertifyHandler,
		EventPublisher eventPublisher,
		TransactionHandler transactionHandler,
		RetryHandler retryHandler
	) {
		this.memberMissionFinder = memberMissionFinder;
		this.missionCertifyHandler = missionCertifyHandler;
		this.eventPublisher = eventPublisher;
		this.transactionHandler = transactionHandler;
		this.retryHandler = retryHandler;
	}

	public void successMission(
		@NotNull MissionCompleteCommand mc,
		@NotNull CertifiedMissionPostCommand pc
	) {
		missionCertifyTrackingService.register(requestId, memberNo, missionNo);

		String imagePathKey = uploadImage(requestId, memberNo, missionNo, content, missionImageFile);
		if (imagePathKey == null) {
			return ResponseEntity.accepted().body(requestId);
		}
		Long point = getReward(requestId, memberNo, missionNo, imagePathKey, content);
		if (point == null) {
			return ResponseEntity.accepted().body(requestId);
		}

		retryHandler.execute(() -> // 실패 발생 시 재시도
			transactionHandler.execute(() -> {
				MemberMission memberMission = memberMissionFinder.find(mc.getMemberNo(), mc.getMissionNo());
				MemberMission successMission = missionCertifyHandler.success(memberMission);
				publishRewardEventForSuccessMission(successMission); // 리워드 포인트 지급 이벤트 발행
				publishCreatePostEventForSuccessMission(pc, successMission); // 게시글 생성 이벤트 발행
				return null;
			})
		);
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
		MemberMission memberMission = memberMissionFinder.find(missionCommand.getMemberNo(),
			missionCommand.getMissionNo());
		missionCertifyHandler.failure(memberMission);
	}
}
