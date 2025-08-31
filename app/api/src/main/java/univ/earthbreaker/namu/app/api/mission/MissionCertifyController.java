package univ.earthbreaker.namu.app.api.mission;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Qualifier;
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
import univ.earthbreaker.namu.external.image.ImageUploadCommand;

@RestController
@RequestMapping("/v2/missions")
public class MissionCertifyController {

	private final ImageManager imageManager;
	private final PointManager pointManager;
	private final MemberMissionCertifyService missionCertifyService;
	private final Executor executor;
	private final KafkaTemplate<String, RetryMessage> kafkaTemplate;

	public MissionCertifyController(
		@Qualifier("externalImageManager") ImageManager imageManager,
		PointManager pointManager,
		MemberMissionCertifyService missionCertifyService,
		Executor threadPoolExecutor,
		KafkaTemplate<String, RetryMessage> kafkaTemplate
	) {
		this.imageManager = imageManager;
		this.pointManager = pointManager;
		this.missionCertifyService = missionCertifyService;
		this.executor = threadPoolExecutor;
		this.kafkaTemplate = kafkaTemplate;
	}

	@AuthMapping
	@PostMapping(path = "/certification/success/{missionNo}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> success(
		@LoginMember Long memberNo,
		@PathVariable Long missionNo,
		@RequestPart(value = "content") String content,
		@RequestPart(value = "imageFile") MultipartFile missionImageFile
	) {
		String imagePathKey = uploadImage(missionImageFile);
		if (imagePathKey == null) {
			return ResponseEntity.accepted().build();
		}
		Long point = getReward();
		if (point == null) {
			return ResponseEntity.accepted().build();
		}
		missionCertifyService.successMission(
			new MissionCompleteCommand(memberNo, missionNo),
			new CertifiedMissionPostCommand(memberNo, content, imagePathKey, point)
		);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	private String uploadImage(MultipartFile missionImageFile) {
		try {
			return imageManager.upload(new ImageUploadCommand(missionImageFile));
		} catch (Exception e) {
			RetryMessage retryMessage = RetryMessage.of(memberNo, missionNo, content, RetryStep.IMAGE_UPLOAD);
			kafkaTemplate.send("earthbreaker.namu.mission-retry", retryMessage.getKey(), retryMessage);
			return null;
		}
	}

	private Long getReward() {
		try {
			return pointManager.reward();
		} catch (Exception e) {
			RetryMessage retryMessage = RetryMessage.of(memberNo, missionNo, content, RetryStep.POINT_ISSUE);
			kafkaTemplate.send("earthbreaker.namu.mission-retry", retryMessage.getKey(), retryMessage);
			return null;
		}
	}
}
