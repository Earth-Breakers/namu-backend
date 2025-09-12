package univ.earthbreaker.namu.app.api.mission;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import univ.earthbreaker.namu.app.support.AuthMapping;
import univ.earthbreaker.namu.app.support.LoginMember;
import univ.earthbreaker.namu.core.domain.mission.MissionCertifyStatus;
import univ.earthbreaker.namu.core.domain.mission.service.CertifyResult;
import univ.earthbreaker.namu.core.domain.mission.service.MissionCertifyFacade;
import univ.earthbreaker.namu.core.domain.mission.service.MissionCertifyTrackingService;

@RestController
@RequestMapping("/v2/missions")
public class MissionCertifyController {

	private final MissionCertifyFacade missionCertifyFacade;
	private final MissionCertifyTrackingService missionCertifyTrackingService;

	public MissionCertifyController(
		MissionCertifyFacade missionCertifyFacade,
		MissionCertifyTrackingService missionCertifyTrackingService
	) {
		this.missionCertifyFacade = missionCertifyFacade;
		this.missionCertifyTrackingService = missionCertifyTrackingService;
	}

	@AuthMapping
	@PostMapping(path = "/certification/success/{missionNo}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<CertifyResult> success(
		@LoginMember Long memberNo,
		@PathVariable Long missionNo,
		@RequestPart(value = "content") String content,
		@RequestPart(value = "imageFile") MultipartFile missionImageFile
	) {
		String requestId = memberNo + ":" + missionNo;
		CertifyResult result = missionCertifyFacade.certify(requestId, memberNo, missionNo, content);
		if (result.isPending()) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@GetMapping("/certification/status/{requestId}")
	public ResponseEntity<MissionCertificationStatusResponse> pollCertifyProcess(@PathVariable String requestId) {
		MissionCertifyStatus status = missionCertifyTrackingService.retrieve(requestId);
		return ResponseEntity.ok(MissionCertificationStatusResponse.from(status));
	}
}
