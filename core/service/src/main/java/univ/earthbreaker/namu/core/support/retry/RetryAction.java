package univ.earthbreaker.namu.core.support.retry;

@FunctionalInterface
public interface RetryAction<T> {
	T action();
}
