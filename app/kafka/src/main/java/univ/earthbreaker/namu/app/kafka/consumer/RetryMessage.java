package univ.earthbreaker.namu.app.kafka.consumer;

public record RetryMessage(
	long memberNo,
	long missionNo,
	String imagePathKey,
	String postContents,
	RetryStep retryStep,
	int attempt
) {
	static final int MAX_RETRY_ATTEMPTS = 3;

	public RetryMessage toNext(RetryStep retryStep) {
		return new RetryMessage(
			memberNo,
			missionNo,
			imagePathKey,
			postContents,
			retryStep,
			attempt + 1
		);
	}
}
