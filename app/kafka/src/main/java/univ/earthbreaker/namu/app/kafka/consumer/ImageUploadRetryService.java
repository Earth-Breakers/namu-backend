package univ.earthbreaker.namu.app.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import univ.earthbreaker.namu.core.domain.mission.CertifiedMissionPostCommand;
import univ.earthbreaker.namu.core.domain.mission.MemberMissionCertifyService;
import univ.earthbreaker.namu.core.domain.mission.MissionCompleteCommand;
import univ.earthbreaker.namu.external.image.ImageManager;
import univ.earthbreaker.namu.external.image.ImageUploadCommand;

@Service
public class ImageUploadRetryService implements MissionRetryer {

	private static final Logger log = LoggerFactory.getLogger(ImageUploadRetryService.class);

	private final ImageManager imageManager;
	private final PointManager pointManager;
	private final MemberMissionCertifyService memberMissionCertifyService;
	private final KafkaTemplate<String, RetryMessage> kafkaTemplate;

	public ImageUploadRetryService(
		ImageManager imageManager,
		PointManager pointManager,
		MemberMissionCertifyService memberMissionCertifyService,
		KafkaTemplate<String, RetryMessage> kafkaTemplate
	) {
		this.imageManager = imageManager;
		this.pointManager = pointManager;
		this.memberMissionCertifyService = memberMissionCertifyService;
		this.kafkaTemplate = kafkaTemplate;
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
