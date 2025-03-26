package univ.earthbreaker.namu.server.external.api;

import java.util.Random;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.server.external.exception.ExternalImageServerException;

/**
 * 응답 지연시간이 100ms 에서 10000ms 로 랜덤한,
 * 동기적으로 동작하는 외부 이미지 서버 API 라고 가정
 */
@Component
public class RandomLatencyImageManager {

	private static final ExternalImageStorage STORAGE = new ExternalImageStorage();
	private static final Random RANDOM = new Random();

	private static final int MIN_DELAY_MS = 100; // 최소 지연 시간
	private static final int MAX_DELAY_MS = 10_000; // 최대 지연 시간
	private static final double EXCEPTION_PROBABILITY = 0.1; // 예외 발생 확률 (10%)

	/**
	 * 동기적으로 이미지를 업로드
	 * @param imageKey 이미지 키
	 * @param imageData 이미지 파일 데이터
	 * @return 업로드 결과 메시지
	 */
	public String uploadImage(String imageKey, String imageData) {
		int delay = getDelay();
		delaySimulation(delay);
		invokeException();

		String uploadedImageKey = STORAGE.upload(imageKey, imageData);
		return uploadedImageKey + " image uploaded successfully after " + delay + "ms";
	}

	/**
	 * @param imageKey 이미지 키
	 * @return 삭제 결과 메시지
	 */
	public String deleteImage(String imageKey) {
		int delay = getDelay();
		delaySimulation(delay);
		invokeException();

		STORAGE.delete(imageKey);
		return "Image deleted successfully after " + delay + "ms";
	}

	/**
	 * @return 랜덤한 지연 시간 (100ms ~ 10000ms)
	 */
	private int getDelay() {
		return RANDOM.nextInt(MAX_DELAY_MS - MIN_DELAY_MS + 1) + MIN_DELAY_MS;
	}

	/**
	 * 지연 시뮬레이션
	 * @param delay 지연시간
	 */
	private void delaySimulation(int delay) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			throw new ExternalImageServerException(e.getMessage());
		}
	}

	/**
	 * 10% 확률로 실패 발생시키기
	 */
	private void invokeException() {
		if (RANDOM.nextDouble() < EXCEPTION_PROBABILITY) {
			throw new ExternalImageServerException("Simulated exception during image upload");
		}
	}
}
