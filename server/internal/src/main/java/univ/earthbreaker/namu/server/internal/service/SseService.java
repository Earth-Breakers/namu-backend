package univ.earthbreaker.namu.server.internal.service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class SseService {

	private final Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

	public void subscribe(String id) {
		long timeout = 1000L * 60; // sse emitter 연결 시간 1분

		SseEmitter sseEmitter = new SseEmitter(timeout);
		sseEmitters.put(id, sseEmitter);

		sseEmitter.onCompletion(() -> sseEmitters.remove(id));
		sseEmitter.onTimeout(sseEmitter::complete);
		sseEmitter.onError(throwable -> sseEmitter.complete());
	}

	public void sendToClient(String id, String eventName, Object data) {
		SseEmitter sseEmitter = sseEmitters.get(id);
		try {
			sseEmitter.send(
				SseEmitter.event()
					.id(id)
					.name(eventName)
					.data(data)
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
