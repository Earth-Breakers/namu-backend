package univ.earthbreaker.namu.app.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import univ.earthbreaker.namu.core.domain.mission.CertifiedMissionPostCommand;
import univ.earthbreaker.namu.core.domain.mission.MemberMissionCertifyService;
import univ.earthbreaker.namu.core.domain.mission.MissionCompleteCommand;

@Service
public class PointIssueRetryService implements MissionRetryer {

	private static final Logger log = LoggerFactory.getLogger(PointIssueRetryService.class);

	private final MemberMissionCertifyService memberMissionCertifyService;
	private final PointManager pointManager;
	private final KafkaTemplate<String, RetryMessage> kafkaTemplate;

	public PointIssueRetryService(
		MemberMissionCertifyService memberMissionCertifyService,
		PointManager pointManager,
		KafkaTemplate<String, RetryMessage> kafkaTemplate
	) {
		this.memberMissionCertifyService = memberMissionCertifyService;
		this.pointManager = pointManager;
		this.kafkaTemplate = kafkaTemplate;
	}

	@Override
	public RetryStep supportStep() {
		return RetryStep.POINT_ISSUE;
	}

	@Override
	public void process(String key, RetryMessage message) {
		Long point = handlePointRetry(key, message);
		if (point == null) { // 포인트 발급 실패 시 메서드 종료
			log.info("Point for upload failed for key: {}, record message : {}", key, message);
			return;
		}
		memberMissionCertifyService.successMission(
			new MissionCompleteCommand(message.memberNo(), message.missionNo()),
			new CertifiedMissionPostCommand(message.memberNo(), message.postContents(), message.imagePathKey(), point)
		);
	}

	private Long handlePointRetry(String key, RetryMessage message) {
		try {
			return pointManager.reward();
		} catch (Exception e) {
			retry(key, message.toNext(RetryStep.POINT_ISSUE), e);
			return null;
		}
	}

	private void retry(String key, RetryMessage message, Exception e) {
		if (RetryMessage.MAX_RETRY_ATTEMPTS >= message.attempt()) {
			kafkaTemplate.send("earthbreaker.namu.mission-retry", key, message);
		} else {
			log.error("Retry failed for key: {}, record message : {}, exception : {}", key, message, e);
		}
	}
}
