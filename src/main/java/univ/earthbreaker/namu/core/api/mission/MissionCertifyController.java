package univ.earthbreaker.namu.core.api.mission;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import univ.earthbreaker.namu.core.api.auth.support.AuthMapping;
import univ.earthbreaker.namu.core.api.auth.support.LoginMember;
import univ.earthbreaker.namu.core.domain.mission.MemberMissionCertifyService;
import univ.earthbreaker.namu.external.aws.image.ImageManager;
import univ.earthbreaker.namu.external.aws.image.ImagePathKeyGenerator;
import univ.earthbreaker.namu.external.aws.image.ImageUploadCommand;

@RestController
@RequestMapping("/v2/missions")
public class MissionCertifyController {

	private final ImageManager imageManager;
	private final ImagePathKeyGenerator imagePathKeyGenerator;
	private final MemberMissionCertifyService missionCertifyService;

	public MissionCertifyController(
		@Qualifier("externalImageManager") ImageManager imageManager,
		@Qualifier("missionPostImagePathGen") ImagePathKeyGenerator imagePathKeyGenerator,
		MemberMissionCertifyService missionCertifyService
	) {
		this.imageManager = imageManager;
		this.imagePathKeyGenerator = imagePathKeyGenerator;
		this.missionCertifyService = missionCertifyService;
	}

	@AuthMapping
	@PostMapping(path = "/certification/success/{missionNo}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> success(
		@LoginMember Long memberNo,
		@PathVariable Long missionNo,
		@RequestPart(value = "content") String content,
		@RequestPart(value = "imageFile") MultipartFile missionImageFile
	) {
		String result = imageManager.upload(ImageUploadCommand.forMember(memberNo, missionImageFile, imagePathKeyGenerator));
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
}
