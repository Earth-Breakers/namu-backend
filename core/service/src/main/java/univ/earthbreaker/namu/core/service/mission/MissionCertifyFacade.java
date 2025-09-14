package univ.earthbreaker.namu.core.service.mission;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.mission.CertifiedMissionPostCommand;
import univ.earthbreaker.namu.core.domain.mission.CertifyResult;
import univ.earthbreaker.namu.core.domain.mission.ImageUploadResult;
import univ.earthbreaker.namu.core.domain.mission.MissionCertifyProcess;
import univ.earthbreaker.namu.core.domain.mission.MissionCompleteCommand;
import univ.earthbreaker.namu.core.domain.mission.PointIssueResult;
import univ.earthbreaker.namu.core.domain.mission.infra.MissionRetryMessagePublisher;
import univ.earthbreaker.namu.core.domain.mission.infra.RetryMessage;
import univ.earthbreaker.namu.core.domain.mission.infra.RetryStep;

@Component
public class MissionCertifyFacade {

	private final MissionImageUploadService imageUploadService;
	private final MissionPointIssueService pointIssueService;
	private final MissionCertifyTrackingService missionCertifyTrackingService;
	private final MemberMissionCertifyService memberMissionCertifyService;
	private final MissionRetryMessagePublisher retryMessagePublisher;

	public MissionCertifyFacade(
		MissionImageUploadService imageUploadService,
		MissionPointIssueService pointIssueService,
		MissionCertifyTrackingService missionCertifyTrackingService,
		MemberMissionCertifyService memberMissionCertifyService,
		MissionRetryMessagePublisher retryMessagePublisher
	) {
		this.imageUploadService = imageUploadService;
		this.pointIssueService = pointIssueService;
		this.missionCertifyTrackingService = missionCertifyTrackingService;
		this.memberMissionCertifyService = memberMissionCertifyService;
		this.retryMessagePublisher = retryMessagePublisher;
	}

	public CertifyResult certify(String requestId, long memberNo, long missionNo, String content) {
		missionCertifyTrackingService.register(requestId, memberNo, missionNo);

		ImageUploadResult uploadResult = imageUploadService.process(requestId, memberNo, missionNo);
		if (!uploadResult.isSuccess()) {
			RetryMessage retryMessage = RetryMessage.create(
				requestId, memberNo, missionNo, uploadResult.imagePathKey(), content, RetryStep.IMAGE_UPLOAD);
			retryMessagePublisher.publish(retryMessage);
			return new CertifyResult(requestId, MissionCertifyProcess.PENDING);
		}

		PointIssueResult pointResult = pointIssueService.process(memberNo, missionNo);
		if (!pointResult.isSuccess()) {
			RetryMessage retryMessage = RetryMessage.create(
				requestId, memberNo, missionNo, uploadResult.imagePathKey(), content, RetryStep.POINT_ISSUE);
			retryMessagePublisher.publish(retryMessage);
			return new CertifyResult(requestId, MissionCertifyProcess.PENDING);
		}

		memberMissionCertifyService.successMission(
			new MissionCompleteCommand(memberNo, missionNo),
			new CertifiedMissionPostCommand(memberNo, content, uploadResult.imagePathKey(), pointResult.point())
		);
		missionCertifyTrackingService.update(requestId, MissionCertifyProcess.COMPLETED);

		return new CertifyResult(requestId, MissionCertifyProcess.COMPLETED);
	}
}
