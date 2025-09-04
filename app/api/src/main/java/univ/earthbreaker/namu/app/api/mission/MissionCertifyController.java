package univ.earthbreaker.namu.app.api.mission;

import static univ.earthbreaker.namu.app.api.config.KafkaProducerConfig.RetryMessage;
import static univ.earthbreaker.namu.app.api.config.KafkaProducerConfig.RetryStep;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import univ.earthbreaker.namu.app.support.AuthMapping;
import univ.earthbreaker.namu.app.support.LoginMember;
import univ.earthbreaker.namu.core.domain.mission.CertifiedMissionPostCommand;
import univ.earthbreaker.namu.core.domain.mission.MemberMissionCertifyService;
import univ.earthbreaker.namu.core.domain.mission.MissionCompleteCommand;
import univ.earthbreaker.namu.external.image.ImageManager;
import univ.earthbreaker.namu.clients.point.PointManager;
import univ.earthbreaker.namu.external.image.ImageUploadCommand;

@RestController
@RequestMapping("/v2/missions")
public class MissionCertifyController {

	private final ImageManager imageManager;
	private final PointManager pointManager;
	private final MemberMissionCertifyService missionCertifyService;
	private final KafkaTemplate<String, RetryMessage> kafkaTemplate;
	private final String missionRetryTopic;

	public MissionCertifyController(
		@Qualifier("externalImageManager") ImageManager imageManager,
		PointManager pointManager,
		MemberMissionCertifyService missionCertifyService,
		KafkaTemplate<String, RetryMessage> kafkaTemplate,
		@Value("${kafka.topics.mission-retry.name}") String missionRetryTopic
	) {
		this.imageManager = imageManager;
		this.pointManager = pointManager;
		this.missionCertifyService = missionCertifyService;
		this.kafkaTemplate = kafkaTemplate;
		this.missionRetryTopic = missionRetryTopic;
	}

	@AuthMapping
	@PostMapping(path = "/certification/success/{missionNo}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> success(
		@LoginMember Long memberNo,
		@PathVariable Long missionNo,
		@RequestPart(value = "content") String content,
		@RequestPart(value = "imageFile") MultipartFile missionImageFile
	) {
		String imagePathKey = uploadImage(memberNo, missionNo, content, missionImageFile);
		if (imagePathKey == null) {
			return ResponseEntity.accepted().build();
		}
		Long point = getReward(memberNo, missionNo, imagePathKey, content);
		if (point == null) {
			return ResponseEntity.accepted().build();
		}
		missionCertifyService.successMission(
			new MissionCompleteCommand(memberNo, missionNo),
			new CertifiedMissionPostCommand(memberNo, content, imagePathKey, point)
		);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	private String uploadImage(Long memberNo, Long missionNo, String content, MultipartFile missionImageFile) {
		try {
			return imageManager.upload(new ImageUploadCommand());
		} catch (Exception e) {
			RetryMessage retryMessage = RetryMessage.create(memberNo, missionNo, null, content, RetryStep.IMAGE_UPLOAD);
			kafkaTemplate.send(missionRetryTopic, retryMessage.getKey(), retryMessage);
			return null;
		}
	}

	private Long getReward(Long memberNo, Long missionNo, String imagePathKey, String content) {
		try {
			return pointManager.issue();
		} catch (Exception e) {
			RetryMessage retryMessage = RetryMessage.create(memberNo, missionNo, imagePathKey, content, RetryStep.POINT_ISSUE);
			kafkaTemplate.send(missionRetryTopic, retryMessage.getKey(), retryMessage);
			return null;
		}
	}

	@GetMapping("/certification/status/{requestId}")
	public ResponseEntity<MissionCertificationStatusResponse> pollCertifyProcess(@PathVariable String requestId) {
		MissionCertifyStatus status = missionCertifyTrackingService.retrieve(requestId);
		return ResponseEntity.ok(MissionCertificationStatusResponse.from(status));
	}
}
