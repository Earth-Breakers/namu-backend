package univ.earthbreaker.namu.server.internal.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.server.internal.service.SseService;

@RestController
@RequestMapping("/internal/sse")
public class InternalSseApiServer {

	private final SseService sseService;

	public InternalSseApiServer(SseService sseService) {
		this.sseService = sseService;
	}

	@GetMapping("/subscribe/{userId}")
	public ResponseEntity<Void> subscribe(@PathVariable String userId) {
		sseService.subscribe(userId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/send/{userId}")
	public ResponseEntity<Void> send(@PathVariable String userId, @RequestBody SseSendRequest request) {
		sseService.sendToClient(userId, request.eventName(), request.data());
		return ResponseEntity.noContent().build();
	}
}
