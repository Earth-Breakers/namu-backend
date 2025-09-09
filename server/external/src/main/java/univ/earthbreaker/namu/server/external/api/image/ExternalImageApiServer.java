package univ.earthbreaker.namu.server.external.api.image;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	public ResponseEntity<ExternalImageResponse> uploadSuccess() {
		String resultMessage = randomLatencyImageManager.uploadImage("", "");
		return ResponseEntity.ok(new ExternalImageResponse(HttpStatus.OK.value(), resultMessage, null));
	}

	@PostMapping("/delete")
	public ResponseEntity<ExternalImageResponse> deleteSuccess() {
		String resultMessage = randomLatencyImageManager.deleteImage("");
		return ResponseEntity.ok(new ExternalImageResponse(HttpStatus.OK.value(), resultMessage, null));
	}

	@GetMapping
	public ResponseEntity<ExternalImageResponse> getSuccess() {
		String imageDataResult = randomLatencyImageManager.getImage("");
		return ResponseEntity.ok(new ExternalImageResponse(
			HttpStatus.OK.value(), "retrieve complete", imageDataResult)
		);
	}
}
