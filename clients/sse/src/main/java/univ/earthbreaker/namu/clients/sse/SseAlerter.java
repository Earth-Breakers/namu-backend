package univ.earthbreaker.namu.clients.sse;

import org.springframework.stereotype.Component;

@Component
public class SseAlerter {

	private final SseApiCaller apiCaller;

	public SseAlerter(SseApiCaller apiCaller) {
		this.apiCaller = apiCaller;
	}

	public void alert(Long userId, String eventName, Object data) {
		apiCaller.send(userId, new SendRequest(eventName, data));
	}
}
