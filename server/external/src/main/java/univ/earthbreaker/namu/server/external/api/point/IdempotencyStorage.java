package univ.earthbreaker.namu.server.external.api.point;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class IdempotencyStorage {

	private final Map<String, ExternalPointResponse> storage = new ConcurrentHashMap<>();

	public ExternalPointResponse get(String key) {
		return storage.get(key);
	}

	public void put(String key, ExternalPointResponse value) {
		storage.put(key, value);
	}
}
