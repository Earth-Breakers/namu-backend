package univ.earthbreaker.namu.app.kafka.consumer;

public interface MissionRetryer {

	RetryStep supportStep();

	void process(String key, RetryMessage message);
}
