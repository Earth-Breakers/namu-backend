package univ.earthbreaker.namu.core.support.retry;

public interface RetryHandler {

	<T> T execute(RetryAction<T> action) throws RuntimeException;

	<T> T execute(RetryAction<T> action, RecoveryAction<T> recovery) throws RuntimeException;
}
