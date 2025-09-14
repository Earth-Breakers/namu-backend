package univ.earthbreaker.namu.app.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import univ.earthbreaker.namu.core.domain.mission.CertifiedMissionPostCommand;
import univ.earthbreaker.namu.core.domain.mission.MissionCertifyProcess;
import univ.earthbreaker.namu.core.domain.mission.MissionCompleteCommand;
import univ.earthbreaker.namu.core.domain.mission.PointIssueResult;
import univ.earthbreaker.namu.core.service.mission.MemberMissionCertifyService;
import univ.earthbreaker.namu.core.service.mission.MissionCertifyTrackingService;
import univ.earthbreaker.namu.core.service.mission.MissionPointIssueService;

@Service
public class PointIssueRetryService implements MissionRetryer {

	private static final Logger log = LoggerFactory.getLogger(PointIssueRetryService.class);

	private final MemberMissionCertifyService memberMissionCertifyService;
	private final MissionCertifyTrackingService missionCertifyTrackingService;
	private final MissionPointIssueService pointIssueService;

	public PointIssueRetryService(
		MemberMissionCertifyService memberMissionCertifyService,
		MissionCertifyTrackingService missionCertifyTrackingService,
		MissionPointIssueService pointIssueService
	) {
		this.memberMissionCertifyService = memberMissionCertifyService;
		this.missionCertifyTrackingService = missionCertifyTrackingService;
		this.pointIssueService = pointIssueService;
	}

	@Override
	public RetryStep supportStep() {
		return RetryStep.POINT_ISSUE;
	}

	@Override
	public void process(String key, RetryMessage message) {
		PointIssueResult pointResult = pointIssueService.process(message.memberNo(), message.missionNo());
		if (!pointResult.isSuccess()) {  // 포인트 발급 실패 시 메서드 종료
			log.info("Point for upload failed for key: {}, record message : {}", key, message);
			return;
		}
		memberMissionCertifyService.successMission(
			new MissionCompleteCommand(message.memberNo(), message.missionNo()),
			new CertifiedMissionPostCommand(message.memberNo(), message.postContents(), message.imagePathKey(), pointResult.point())
		);
		missionCertifyTrackingService.update(key, MissionCertifyProcess.COMPLETED);
	}
}
