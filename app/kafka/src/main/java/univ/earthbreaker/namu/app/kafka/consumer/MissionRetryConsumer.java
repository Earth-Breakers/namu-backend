package univ.earthbreaker.namu.app.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.mission.CertifiedMissionPostCommand;
import univ.earthbreaker.namu.core.domain.mission.MemberMissionCertifyService;
import univ.earthbreaker.namu.core.domain.mission.MissionCompleteCommand;
import univ.earthbreaker.namu.external.image.ImageManager;
import univ.earthbreaker.namu.external.image.ImageUploadCommand;

@Component
public class MissionRetryConsumer {

	private final ImageManager imageManager;
	private final PointManager pointManager;
	private final MemberMissionCertifyService memberMissionCertifyService;
	private final KafkaTemplate<String, RetryMessage> kafkaTemplate;

	public MissionRetryConsumer(
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

	@KafkaListener(topics = "earthbreaker.namu.mission-retry", groupId = "${spring.kafka.consumer.group-id}")
	public void handleRetry(ConsumerRecord<String, RetryMessage> recordEvent) {
		RetryMessage message = recordEvent.value();
		Long point;
		switch (message.retryStep()) {
			case IMAGE_UPLOAD:
				if (!handleImageUploadRetry(recordEvent.key(), message)) { // 이미지 업로드 실패 시 메서드 종료
					return;
				}
				point = handlePointRetry(recordEvent.key(), message);
				if (point == null) {  // 포인트 발급 실패 시 메서드 종료
					return;
				}
				memberMissionCertifyService.successMission(
					new MissionCompleteCommand(message.memberNo(), message.missionNo()),
					new CertifiedMissionPostCommand(message.memberNo(), message.postContents(), message.imagePathKey()),
					point
				);
				break;
			case POINT_ISSUE:
				point = handlePointRetry(recordEvent.key(), message);
				if (point == null) { // 포인트 발급 실패 시 메서드 종료
					return;
				}
				memberMissionCertifyService.successMission(
					new MissionCompleteCommand(message.memberNo(), message.missionNo()),
					new CertifiedMissionPostCommand(message.memberNo(), message.postContents(), message.imagePathKey()),
					point
				);
				break;
			default:
				break;
		}
	}

	private boolean handleImageUploadRetry(String key, RetryMessage message) {
		try {
			if (imageManager.retrieve(message.imagePathKey()) == null) {
				imageManager.upload(new ImageUploadCommand());
			}
			return true;
		} catch (Exception e) {
			retry(key, message.toNext(RetryStep.IMAGE_UPLOAD));
			return false;
		}
	}

	private Long handlePointRetry(String key, RetryMessage message) {
		try {
			return pointManager.reword();
		} catch (Exception e) {
			retry(key, message.toNext(RetryStep.POINT_ISSUE));
			return null;
		}
	}

	private void retry(String key, RetryMessage message) {
		if (RetryMessage.MAX_RETRY_ATTEMPTS >= message.attempt()) {
			kafkaTemplate.send("earthbreaker.namu.mission-retry", key, message);
		}
	}
}
