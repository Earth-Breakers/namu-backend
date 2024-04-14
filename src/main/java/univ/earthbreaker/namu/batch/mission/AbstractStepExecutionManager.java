package univ.earthbreaker.namu.batch.mission;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;

import univ.earthbreaker.namu.batch.BatchException;

public abstract class AbstractStepExecutionManager<T> {

	static final String MISSIONS_PROMOTION_KEY = "SHARED_MISSIONS";

	private StepExecution stepExecution;

	protected void putData(String key, T value) {
		validateStepExecutionIsNull();
		ExecutionContext executionContext = stepExecution.getExecutionContext();
		executionContext.put(key, value);
	}

	protected Object getData(String key) {
		validateStepExecutionIsNull();
		JobExecution jobExecution = stepExecution.getJobExecution();
		ExecutionContext executionContext = jobExecution.getExecutionContext();
		return executionContext.get(key);
	}

	private void validateStepExecutionIsNull() {
		if (stepExecution == null) {
			throw BatchException.isStepExecutionNull();
		}
	}

	protected void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}
}
