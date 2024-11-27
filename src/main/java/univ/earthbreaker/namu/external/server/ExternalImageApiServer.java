package univ.earthbreaker.namu.external.server;

import org.springframework.http.HttpStatusCode;
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

	@PostMapping("/upload/success")
	public ResponseEntity<ExternalImageResponse> uploadSuccess() {
		String result = randomLatencyImageManager.uploadImage("", "");
		return new ResponseEntity<>(
			new ExternalImageResponse(200, result),
			HttpStatusCode.valueOf(200)
		);
	}

	@PostMapping("/upload/fail")
	public ResponseEntity<ExternalImageResponse> uploadFail() {
		return new ResponseEntity<>(
			new ExternalImageResponse(400, "image upload fail"),
			HttpStatusCode.valueOf(400)
		);
	}

	@PostMapping("/delete/success")
	public ResponseEntity<ExternalImageResponse> deleteSuccess() {
		String result = randomLatencyImageManager.deleteImage("");
		return new ResponseEntity<>(
			new ExternalImageResponse(200, result),
			HttpStatusCode.valueOf(200)
		);
	}
}
