package univ.earthbreaker.namu.core.support;

@FunctionalInterface
public interface TransactionAction<T> {
	T action();
}
