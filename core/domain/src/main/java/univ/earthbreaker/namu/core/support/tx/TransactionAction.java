package univ.earthbreaker.namu.core.support.tx;

@FunctionalInterface
public interface TransactionAction<T> {
	T action();
}
