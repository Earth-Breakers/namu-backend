package univ.earthbreaker.namu.app.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import univ.earthbreaker.namu.clients.point.PointManager;
import univ.earthbreaker.namu.core.domain.mission.CertifiedMissionPostCommand;
import univ.earthbreaker.namu.core.domain.mission.MemberMissionCertifyService;
import univ.earthbreaker.namu.core.domain.mission.MissionCertifyProcess;
import univ.earthbreaker.namu.core.domain.mission.MissionCertifyTrackingService;
import univ.earthbreaker.namu.core.domain.mission.MissionCompleteCommand;
import univ.earthbreaker.namu.external.image.ImageManager;
import univ.earthbreaker.namu.external.image.ImageUploadCommand;

@Service
public class ImageUploadRetryService implements MissionRetryer {

	private static final Logger log = LoggerFactory.getLogger(ImageUploadRetryService.class);

	private final ImageManager imageManager;
	private final PointManager pointManager;
	private final MemberMissionCertifyService memberMissionCertifyService;
	private final MissionCertifyTrackingService missionCertifyTrackingService;
	private final KafkaTemplate<String, RetryMessage> kafkaTemplate;
	private final String missionRetryTopic;

	public ImageUploadRetryService(
		ImageManager imageManager,
		PointManager pointManager,
		MemberMissionCertifyService memberMissionCertifyService,
		MissionCertifyTrackingService missionCertifyTrackingService,
		KafkaTemplate<String, RetryMessage> kafkaTemplate,
		@Value("${kafka.topics.mission-retry.name}") String missionRetryTopic
	) {
		this.imageManager = imageManager;
		this.pointManager = pointManager;
		this.memberMissionCertifyService = memberMissionCertifyService;
		this.missionCertifyTrackingService = missionCertifyTrackingService;
		this.kafkaTemplate = kafkaTemplate;
		this.missionRetryTopic = missionRetryTopic;
	}

	@Override
	public RetryStep supportStep() {
		return RetryStep.IMAGE_UPLOAD;
	}

	@Override
	public void process(String key, RetryMessage message) {
		if (!handleImageUploadRetry(key, message)) { // 이미지 업로드 실패 시 메서드 종료
			log.info("Image upload failed for key: {}, record message : {}", key, message);
			return;
		}
		Long point = handlePointRetry(key, message);
		if (point == null) {  // 포인트 발급 실패 시 메서드 종료
			log.info("Point for upload failed for key: {}, record message : {}", key, message);
			return;
		}
		memberMissionCertifyService.successMission(
			new MissionCompleteCommand(message.memberNo(), message.missionNo()),
			new CertifiedMissionPostCommand(message.memberNo(), message.postContents(), message.imagePathKey(), point)
		);
		missionCertifyTrackingService.update(key, MissionCertifyProcess.COMPLETED);
	}

	private boolean handleImageUploadRetry(String key, RetryMessage message) {
		try {
			if (imageManager.retrieve(message.imagePathKey()) == null) {
				imageManager.upload(new ImageUploadCommand());
			}
			return true;
		} catch (Exception e) {
			retry(key, message.toNext(RetryStep.IMAGE_UPLOAD), e);
			return false;
		}
	}

	private Long handlePointRetry(String key, RetryMessage message) {
		try {
			return pointManager.issuePoint(message.memberNo(), message.missionNo());
		} catch (Exception e) {
			retry(key, message.toNext(RetryStep.POINT_ISSUE), e);
			return null;
		}
	}

	private void retry(String key, RetryMessage message, Exception e) {
		if (RetryMessage.MAX_RETRY_ATTEMPTS >= message.attempt()) {
			kafkaTemplate.send(missionRetryTopic, key, message);
		} else {
			missionCertifyTrackingService.update(key, MissionCertifyProcess.FAILED);
			log.error("Retry failed for key: {}, record message : {}, exception : {}", key, message, e);
		}
	}
}
