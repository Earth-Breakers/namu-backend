package univ.earthbreaker.namu.server.external.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/external/image/")
public class ExternalImageApiServer {

	private final RandomLatencyImageManager randomLatencyImageManager;

	public ExternalImageApiServer(RandomLatencyImageManager randomLatencyImageManager) {
		this.randomLatencyImageManager = randomLatencyImageManager;
	}

	@PostMapping("/upload")
	public ResponseEntity<ExternalImageResponse> uploadSuccess() {
		String result = randomLatencyImageManager.uploadImage("", "");
		return ResponseEntity.ok(new ExternalImageResponse(HttpStatus.OK.value(), result));
	}

	@PostMapping("/delete")
	public ResponseEntity<ExternalImageResponse> deleteSuccess() {
		String result = randomLatencyImageManager.deleteImage("");
		return ResponseEntity.ok(new ExternalImageResponse(HttpStatus.OK.value(), result));
	}
}
