// package univ.earthbreaker.namu.core.admin.api;
//
// import java.io.IOException;
// import java.util.List;
//
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestPart;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;
//
// import univ.earthbreaker.namu.external.aws.image.ImageManager;
// import univ.earthbreaker.namu.external.aws.image.ImagePathKeyGenerator;
// import univ.earthbreaker.namu.external.aws.image.ImageUploadCommand;
//
// @RestController
// @RequestMapping("/v1/admin")
// public class AdminImageController {
//
// 	private static final Logger ASYNC_LOGGER = LoggerFactory.getLogger("NamuAsyncLogger");
//
// 	private static final String ADMIN_IMAGE_DIR_KEY = "admin";
//
// 	private final ImageManager imageManager;
// 	private final ImagePathKeyGenerator imagePathKeyGenerator;
//
// 	public AdminImageController(
// 		@Qualifier("awsS3ImageManager") ImageManager imageManager,
// 		@Qualifier("defaultCharacterImagePathGen") ImagePathKeyGenerator imagePathKeyGenerator
// 	) {
// 		this.imageManager = imageManager;
// 		this.imagePathKeyGenerator = imagePathKeyGenerator;
// 	}
//
// 	@PostMapping(path = "/upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
// 	public ResponseEntity<Void> upload(@RequestPart(value = "imageFile") MultipartFile imageFile) throws IOException {
// 		ImageUploadCommand imageUploadCommand = new ImageUploadCommand(
// 			ADMIN_IMAGE_DIR_KEY,
// 			imageFile.getContentType(),
// 			imageFile.getSize(),
// 			imageFile.getOriginalFilename(),
// 			imageFile.getInputStream(),
// 			imagePathKeyGenerator
// 		);
// 		String imagePathKey = imageManager.upload(imageUploadCommand);
// 		ASYNC_LOGGER.info("image-path-key : {}", imagePathKey);
// 		return ResponseEntity.ok().build();
// 	}
//
// 	@PostMapping(path = "/upload-images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
// 	public ResponseEntity<Void> uploadAll(@RequestPart(value = "imageFile") List<MultipartFile> imageFiles) throws
// 		IOException {
// 		for (MultipartFile imageFile : imageFiles) {
// 			String imagePathKey = imageManager.upload(
// 				new ImageUploadCommand(
// 					ADMIN_IMAGE_DIR_KEY,
// 					imageFile.getContentType(),
// 					imageFile.getSize(),
// 					imageFile.getOriginalFilename(),
// 					imageFile.getInputStream(),
// 					imagePathKeyGenerator
// 				));
// 			ASYNC_LOGGER.info("image-path-key : {}", imagePathKey);
// 		}
// 		return ResponseEntity.ok().build();
// 	}
// }
