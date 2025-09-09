package univ.earthbreaker.namu.server.external.api.point;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/external/point")
public class ExternalPointApiServer {

	private final ZzaptarbucksRandomLatencyPointManager pointManager;
	private final IdempotencyStorage idempotencyStorage;

	public ExternalPointApiServer(
		ZzaptarbucksRandomLatencyPointManager pointManager,
		IdempotencyStorage idempotencyStorage
	) {
		this.pointManager = pointManager;
		this.idempotencyStorage = idempotencyStorage;
	}

	@PostMapping("/issue")
	public ResponseEntity<ExternalPointResponse> issuePoint(
		@RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey,
		@RequestBody PointIssueRequest request
	) {
		if (idempotencyKey != null) {
			ExternalPointResponse cached = idempotencyStorage.get(idempotencyKey);
			if (cached != null) {
				return ResponseEntity.ok(cached);
			}
		}

		Long point = pointManager.issue();
		ExternalPointResponse response = new ExternalPointResponse(HttpStatus.OK.value(), "success", point);

		if (idempotencyKey != null) {
			idempotencyStorage.put(idempotencyKey, response);
		}

		return ResponseEntity.ok(response);
	}
}
