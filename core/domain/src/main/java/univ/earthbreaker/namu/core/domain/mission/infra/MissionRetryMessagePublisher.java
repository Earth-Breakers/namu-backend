package univ.earthbreaker.namu.core.domain.mission.infra;

public interface MissionRetryMessagePublisher {
	void publish(RetryMessage message);
}
