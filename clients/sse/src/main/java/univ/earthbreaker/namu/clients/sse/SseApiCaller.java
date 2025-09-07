package univ.earthbreaker.namu.clients.sse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
	name = "sseApiCaller",
	url = "http://localhost:8083/internal/sse")
@Component
public interface SseApiCaller {

	@GetMapping("/subscribe/{userId}")
	void subscribe(@PathVariable Long userId);

	@PostMapping("/send/{userId}")
	void send(@PathVariable Long userId, @RequestBody SendRequest request);
}
