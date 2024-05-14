package univ.earthbreaker.namu.batch.mission;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;

import univ.earthbreaker.namu.batch.BatchException;

public abstract class AbstractExecutionContextManager<T> {

	static final String MISSIONS_PROMOTION_KEY = "SHARED_MISSIONS";

	private StepExecution stepExecution;

	protected AbstractExecutionContextManager() {
	}

	protected void putDataToStepExecutionContext(String key, T value) {
		validateStepExecutionIsNull();
		ExecutionContext stepExecutionContext = stepExecution.getExecutionContext();
		stepExecutionContext.put(key, value);
	}

	/**
	 * ClassCastException 을 발생시킬 가능성이 있는 일반적인 캐스트와 달리,
	 * AbstractExecutionContextManager 클래스를 상속한 클래스들은
	 * T 타입으로 제한되기 때문에 해당 캐스트는 안전합니다
	 * @param key ExecutionContext 에 저장한 <b>공유 데이터</b>의 key 값
	 * @return 공유 데이터
	 */
	@SuppressWarnings("unchecked")
	protected T getDataFromJobExecutionContext(String key) {
		validateStepExecutionIsNull();
		JobExecution jobExecution = stepExecution.getJobExecution();
		ExecutionContext jobExecutionContext = jobExecution.getExecutionContext();
		return (T)jobExecutionContext.get(key);
	}

	private void validateStepExecutionIsNull() {
		if (stepExecution == null) {
			throw BatchException.isStepExecutionNull();
		}
	}

	protected void setCurrentStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}
}
