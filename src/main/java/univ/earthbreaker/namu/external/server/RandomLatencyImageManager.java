package univ.earthbreaker.namu.external.server;

import java.util.Random;

import org.springframework.stereotype.Component;

/**
 * 응답 지연시간이 100ms 에서 10000ms 로 랜덤한,
 * 동기적으로 동작하는 외부 이미지 서버 API 라고 가정
 */
@Component
public class RandomLatencyImageManager {

	private static final int MIN_DELAY_MS = 100; // 최소 지연 시간
	private static final int MAX_DELAY_MS = 10_000; // 최대 지연 시간

	private final Random random = new Random();

	/**
	 * 동기적으로 이미지를 업로드하는 메서드.
	 * @param s1
	 * @param s2
	 * @return 업로드 결과 메시지
	 * @throws InterruptedException 지연 시 예외 발생 가능
	 */
	public String uploadImage(String s1, String s2) {
		// 랜덤한 지연 시간 생성 (100ms ~ 10000ms)
		int delay = random.nextInt(MAX_DELAY_MS - MIN_DELAY_MS + 1) + MIN_DELAY_MS;

		// 지연 시뮬레이션
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		// 업로드 처리 결과 반환
		return "Image uploaded successfully after " + delay + "ms";
	}

	public String deleteImage(String imagePathKey) {
		// 랜덤한 지연 시간 생성 (100ms ~ 10000ms)
		int delay = random.nextInt(MAX_DELAY_MS - MIN_DELAY_MS + 1) + MIN_DELAY_MS;

		// 지연 시뮬레이션
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		return "Image deleted successfully after " + delay + "ms";
	}
}
