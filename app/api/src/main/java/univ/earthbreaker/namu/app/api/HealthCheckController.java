package univ.earthbreaker.namu.app.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

	@GetMapping("/health")
	public ResponseEntity<String> health() {
		return ResponseEntity.ok("health check");
	}
}
