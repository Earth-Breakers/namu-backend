package univ.earthbreaker.namu.server.external.api;

import java.util.HashMap;
import java.util.Map;

import univ.earthbreaker.namu.server.external.exception.ExternalBadRequestException;
import univ.earthbreaker.namu.server.external.exception.ExternalImageServerException;

public class ExternalImageStorage {

	private final Map<String, String> storage = new HashMap<>();

	public String upload(String imageKey, String imageData) {
		if (storage.get(imageKey) != null) { // 중복 검사
			throw new ExternalBadRequestException("이미 저장된 이미지입니다");
		}
		storage.put(imageKey, imageData);
		return imageKey;
	}

	public void delete(String imageKey) {
		if (storage.remove(imageKey) == null) {
			throw new ExternalBadRequestException("Image with Key " + imageKey + " not found.");
		}
	}

	public String download(String imageKey) {
		if (imageKey == null) {
			throw new ExternalBadRequestException("Image ID must not be null.");
		}

		String imageData = storage.get(imageKey);
		if (imageData == null) {
			throw new ExternalImageServerException("Image with ID " + imageKey + " not found.");
		}

		return imageData;
	}
}
