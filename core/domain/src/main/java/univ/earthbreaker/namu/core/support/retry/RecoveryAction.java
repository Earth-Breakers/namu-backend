package univ.earthbreaker.namu.core.support.retry;

@FunctionalInterface
public interface RecoveryAction<T> {
	T recover(Throwable cause);
}
