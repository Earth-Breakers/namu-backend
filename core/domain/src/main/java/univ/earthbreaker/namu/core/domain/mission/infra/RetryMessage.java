package univ.earthbreaker.namu.core.domain.mission.infra;

public record RetryMessage(
	String requestId,
	long memberNo,
	long missionNo,
	String imagePathKey,
	String postContents,
	RetryStep retryStep,
	int attempt,
	int version
) {
	public static RetryMessage create(
		String requestId,
		long memberNo,
		long missionNo,
		String imagePathKey,
		String postContents,
		RetryStep retryStep
	) {
		return new RetryMessage(requestId, memberNo, missionNo, imagePathKey, postContents, retryStep, 1, 1);
	}

	public String getKey() {
		return requestId;
	}
}
