package univ.earthbreaker.namu.external.notification;

import org.springframework.stereotype.Component;

import com.google.api.client.util.BackOff;

@Component
public class FirebaseBackOff implements BackOff {

	private static final long MIN_RETRY_COUNT = 0;
	private static final long MAX_RETRY_COUNT = 3;
	private static final long INITIAL_INTERVAL = 1_000;
	private static final long MAX_INTERVAL = 5_000;

	private Long currentRetryCount = MIN_RETRY_COUNT;
	private Long currentInterval = INITIAL_INTERVAL;

	@Override
	public long nextBackOffMillis() {
		if (isStopped()) {
			return MAX_RETRY_COUNT;
		}
		long exponentialInterval = INITIAL_INTERVAL * (1L << currentRetryCount);
		long nextInterval = Math.min(exponentialInterval, MAX_INTERVAL);
		currentInterval = nextInterval;
		currentRetryCount++;
		return nextInterval;
	}

	@Override
	public void reset() {
		currentRetryCount = MIN_RETRY_COUNT;
		currentInterval = INITIAL_INTERVAL;
	}

	boolean isStopped() {
		return currentRetryCount.equals(MAX_RETRY_COUNT);
	}

	void waitUntilInterval() {
		try {
			Thread.sleep(getCurrentInterval());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw NotificationException.threadInterrupt(e.getMessage());
		}
	}

	long getCurrentRetryCount() {
		return currentRetryCount;
	}

	long getCurrentInterval() {
		return currentInterval;
	}
}
