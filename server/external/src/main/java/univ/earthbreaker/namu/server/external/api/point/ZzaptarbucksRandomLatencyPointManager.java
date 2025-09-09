package univ.earthbreaker.namu.server.external.api.point;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.server.external.exception.ExternalPointServerException;

@Component
public class ZzaptarbucksRandomLatencyPointManager {

	private static final AtomicLong POINT = new AtomicLong(100_000_000) ;
	private static final Random RANDOM = new Random();

	private static final long FIXED_REWARD_POINT = 10;
	private static final int MIN_DELAY_MS = 100; // 최소 지연 시간
	private static final int MAX_DELAY_MS = 3_000; // 최대 지연 시간
	private static final double EXCEPTION_PROBABILITY = 0.01; // 예외 발생 확률 (1%)

	/**
	 * 총 1억 포인트에 대해서, 사용자 별로 10포인트 씩 제공
	 * @throws IllegalStateException : 준비된 1억 포인트가 모두 사라진 경우 예외 발생
	 * @return 10포인트
	 */
	public Long issue() {
		int delay = getDelay();
		delaySimulation(delay);
		invokeException();

		POINT.updateAndGet(current -> {
			long next = current - FIXED_REWARD_POINT;
			if (next < 0) {
				throw new IllegalStateException("The point has been reached");
			}
			return next;
		});

		return FIXED_REWARD_POINT; // 지급한 포인트만 반환
	}

	/**
	 * @return 랜덤한 지연 시간 (100ms ~ 3000ms)
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
			throw new ExternalPointServerException(e.getMessage());
		}
	}

	/**
	 * 1% 확률로 ReadTimeout Exception 실패 발생시키기
	 */
	private void invokeException() {
		if (RANDOM.nextDouble() < EXCEPTION_PROBABILITY) {
			throw new ExternalPointServerException("Read Timeout Exception");
		}
	}
}
