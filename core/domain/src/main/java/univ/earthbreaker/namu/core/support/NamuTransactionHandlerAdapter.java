package univ.earthbreaker.namu.core.support;

import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionTemplate;

@Component
class NamuTransactionHandlerAdapter implements TransactionHandler {

	private final TransactionTemplate transactionTemplate;

	public NamuTransactionHandlerAdapter(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	@Override
	public <T> T execute(TransactionAction<T> action) throws TransactionException {
		return transactionTemplate.execute(status -> action.action());
	}
}
