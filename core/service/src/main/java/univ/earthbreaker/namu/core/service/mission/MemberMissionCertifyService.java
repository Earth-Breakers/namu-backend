package univ.earthbreaker.namu.core.service.mission;

import org.springframework.stereotype.Service;

import univ.earthbreaker.namu.core.domain.mission.CertifiedMissionPostCommand;
import univ.earthbreaker.namu.core.domain.mission.MemberMission;
import univ.earthbreaker.namu.core.domain.mission.MissionCompleteCommand;
import univ.earthbreaker.namu.core.support.retry.RetryHandler;
import univ.earthbreaker.namu.core.support.tx.TransactionHandler;
import univ.earthbreaker.namu.event.EventPublisher;
import univ.earthbreaker.namu.event.point.AddRewardPointEvent;
import univ.earthbreaker.namu.event.post.PostCreateEvent;

@Service
public class MemberMissionCertifyService {

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
		MissionCompleteCommand mc,
		CertifiedMissionPostCommand pc
	) {
		retryHandler.execute(() -> // 실패 발생 시 재시도
			transactionHandler.execute(() -> {
				MemberMission memberMission = memberMissionFinder.find(mc.memberNo(), mc.missionNo());
				MemberMission successMission = missionCertifyHandler.success(memberMission);
				publishRewardEventForSuccessMission(successMission); // 리워드 포인트 지급 이벤트 발행
				publishCreatePostEventForSuccessMission(pc, successMission); // 게시글 생성 이벤트 발행
				return null;
			})
		);
	}

	private void publishRewardEventForSuccessMission(MemberMission successMission) {
		eventPublisher.publish(new AddRewardPointEvent(successMission.getMemberNo(), successMission.getRewardPoint()));
	}

	private void publishCreatePostEventForSuccessMission(
		CertifiedMissionPostCommand postCommand,
		MemberMission successMission
	) {
		eventPublisher.publish(
			new PostCreateEvent(
				postCommand.memberNo(),
				successMission.getActivity(),
				postCommand.content(),
				postCommand.imagePathKey(),
				successMission.getNo())
		);
	}

	public void failureMission(MissionCompleteCommand missionCommand) {
		MemberMission memberMission = memberMissionFinder.find(missionCommand.memberNo(), missionCommand.missionNo());
		missionCertifyHandler.failure(memberMission);
	}
}
