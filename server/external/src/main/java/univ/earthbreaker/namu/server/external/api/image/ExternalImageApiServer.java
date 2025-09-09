package univ.earthbreaker.namu.server.external.api.image;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/external/image")
public class ExternalImageApiServer {

	private final RandomLatencyImageManager randomLatencyImageManager;

	public ExternalImageApiServer(RandomLatencyImageManager randomLatencyImageManager) {
		this.randomLatencyImageManager = randomLatencyImageManager;
	}

	@PostMapping("/upload")
	public ResponseEntity<ExternalImageResponse> uploadSuccess(@RequestBody ImageUploadRequest request) {
		String sampleImageData = String.format("%s:%s:%s", request.bucketName(), request.key(), request.objectMetaData().getContentType());
		String resultMessage = randomLatencyImageManager.uploadImage(request.key(), sampleImageData);
		return ResponseEntity.ok(new ExternalImageResponse(HttpStatus.OK.value(), resultMessage, null));
	}

	@PostMapping("/delete")
	public ResponseEntity<ExternalImageResponse> deleteSuccess(@RequestBody ImageRequest request) {
		String resultMessage = randomLatencyImageManager.deleteImage(request.key());
		return ResponseEntity.ok(new ExternalImageResponse(HttpStatus.OK.value(), resultMessage, null));
	}

	@GetMapping
	public ResponseEntity<ExternalImageResponse> retrieveImage(@RequestBody ImageRequest request) {
		String imageDataResult = randomLatencyImageManager.getImage(request.key());
		return ResponseEntity.ok(new ExternalImageResponse(
			HttpStatus.OK.value(), "retrieve complete", imageDataResult)
		);
	}
}
