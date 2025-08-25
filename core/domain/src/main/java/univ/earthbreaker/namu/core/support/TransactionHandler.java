package univ.earthbreaker.namu.core.support;

import org.springframework.transaction.TransactionException;

public interface TransactionHandler {
	<T> T execute(TransactionAction<T> action) throws TransactionException;
}
