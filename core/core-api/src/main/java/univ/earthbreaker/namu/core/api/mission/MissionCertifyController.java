// package univ.earthbreaker.namu.core.api.mission;
//
// import java.util.concurrent.CompletableFuture;
// import java.util.concurrent.Executor;
//
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestPart;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;
//
// import univ.earthbreaker.namu.core.support.AuthMapping;
// import univ.earthbreaker.namu.core.support.LoginMember;
// import univ.earthbreaker.namu.core.domain.mission.CertifiedMissionPostCommand;
// import univ.earthbreaker.namu.core.domain.mission.MemberMissionCertifyService;
// import univ.earthbreaker.namu.core.domain.mission.MissionCompleteCommand;
// import univ.earthbreaker.namu.external.aws.image.ImageManager;
// import univ.earthbreaker.namu.external.aws.image.ImagePathKeyGenerator;
// import univ.earthbreaker.namu.external.aws.image.ImageUploadCommand;
//
// @RestController
// @RequestMapping("/v2/missions")
// public class MissionCertifyController {
//
// 	private final ImageManager imageManager;
// 	private final ImagePathKeyGenerator imagePathKeyGenerator;
// 	private final MemberMissionCertifyService missionCertifyService;
// 	private final Executor executor;
//
// 	public MissionCertifyController(
// 		@Qualifier("externalImageManager") ImageManager imageManager,
// 		@Qualifier("missionPostImagePathGen") ImagePathKeyGenerator imagePathKeyGenerator,
// 		MemberMissionCertifyService missionCertifyService,
// 		Executor threadPoolExecutor
// 	) {
// 		this.imageManager = imageManager;
// 		this.imagePathKeyGenerator = imagePathKeyGenerator;
// 		this.missionCertifyService = missionCertifyService;
// 		this.executor = threadPoolExecutor;
// 	}
//
// 	@AuthMapping
// 	@PostMapping(path = "/certification/success/{missionNo}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
// 	public ResponseEntity<String> success(
// 		@LoginMember Long memberNo,
// 		@PathVariable Long missionNo,
// 		@RequestPart(value = "content") String content,
// 		@RequestPart(value = "imageFile") MultipartFile missionImageFile
// 	) {
// 		CompletableFuture.supplyAsync(
// 				() -> imageManager.upload(ImageUploadCommand.forMember(memberNo, missionImageFile, imagePathKeyGenerator)),
// 				executor
// 			)
// 			.exceptionally(ex -> null)
// 			.thenAccept(result -> {
// 				if (result != null) {
// 					missionCertifyService.successMission(
// 						new MissionCompleteCommand(memberNo, missionNo),
// 						new CertifiedMissionPostCommand(memberNo, content, result)
// 					);
// 				}
// 			});
// 		return ResponseEntity.status(HttpStatus.CREATED).build();
// 	}
// }
