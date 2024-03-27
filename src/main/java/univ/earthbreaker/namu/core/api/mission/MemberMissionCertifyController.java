package univ.earthbreaker.namu.core.api.mission;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import univ.earthbreaker.namu.core.api.auth.support.AuthMapping;
import univ.earthbreaker.namu.core.api.auth.support.LoginMember;
import univ.earthbreaker.namu.core.domain.mission.CertifiedMissionPostCommand;
import univ.earthbreaker.namu.core.domain.mission.MemberMissionCertifyService;
import univ.earthbreaker.namu.core.domain.mission.MissionCompleteCommand;
import univ.earthbreaker.namu.external.aws.image.ImageManager;
import univ.earthbreaker.namu.external.aws.image.ImageUploadCommand;

@RestController
@AuthMapping
@RequestMapping("/v1/missions")
public class MemberMissionCertifyController {

	private final MemberMissionCertifyService memberMissionCertifyService;
	private final ImageManager imageManager;

	public MemberMissionCertifyController(
		MemberMissionCertifyService memberMissionCertifyService,
		ImageManager imageManager
	) {
		this.memberMissionCertifyService = memberMissionCertifyService;
		this.imageManager = imageManager;
	}

	@PostMapping(path = "/certification/success/{missionNo}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> success(
		@LoginMember Long memberNo,
		@PathVariable Long missionNo,
		@RequestPart(value = "content") String content,
		@RequestPart(value = "imageFile") MultipartFile missionImageFile
	) {
		String imagePathKey = imageManager.upload(new ImageUploadCommand(memberNo, missionImageFile));
		memberMissionCertifyService.successMission(
			new MissionCompleteCommand(memberNo, missionNo),
			new CertifiedMissionPostCommand(memberNo, content, imagePathKey)
		);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PatchMapping("/certification/fail/{missionNo}")
	public ResponseEntity<Void> failure(@LoginMember Long memberNo, @PathVariable Long missionNo) {
		memberMissionCertifyService.failureMission(new MissionCompleteCommand(memberNo, missionNo));
		return ResponseEntity.ok().build();
	}
}
