package univ.earthbreaker.namu.batch;

import org.jetbrains.annotations.NotNull;

public class BatchException extends RuntimeException {

	private BatchException(String message) {
		super(message);
	}

	public static @NotNull BatchException isStepExecutionNull() {
		return new BatchException("[Mission 세팅 JOB] : StepExecution 가 null 값으로, 제대로 설정되지 않았습니다");
	}

	public static @NotNull BatchException pagingQueryCreationFailed(String message) {
		return new BatchException(String.format("[Mission 세팅 JOB] : Paging Query 생성에 실패했습니다 - %s", message));
	}

	public static @NotNull BatchException retrieveStepExecutionDataFailed() {
		return new BatchException("@BeforeStep 이 정상 동작하지 않았습니다");
	}
}
