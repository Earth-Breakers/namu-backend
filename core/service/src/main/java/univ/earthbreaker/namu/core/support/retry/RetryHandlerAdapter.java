package univ.earthbreaker.namu.core.support.retry;

import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Component
public class RetryHandlerAdapter implements RetryHandler {

	private final RetryTemplate retryTemplate;

	public RetryHandlerAdapter(RetryTemplate retryTemplate) {
		this.retryTemplate = retryTemplate;
	}

	@Override
	public <T> T execute(RetryAction<T> action) throws RuntimeException {
		return retryTemplate.execute(context -> action.action());
	}

	@Override
	public <T> T execute(RetryAction<T> action, RecoveryAction<T> recovery) throws RuntimeException {
		return retryTemplate.execute(
			context -> action.action(),
			context -> recovery.recover(context.getLastThrowable())
		);
	}
}
