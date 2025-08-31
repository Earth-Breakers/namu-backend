package univ.earthbreaker.namu.app.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MissionRetryConsumer {

	private final MissionRetryerFactory missionRetryerFactory;

	public MissionRetryConsumer(MissionRetryerFactory missionRetryerFactory) {
		this.missionRetryerFactory = missionRetryerFactory;
	}

	@KafkaListener(topics = {"earthbreaker.namu.mission-retry"}, groupId = "${spring.kafka.consumer.group-id}")
	public void handleRetry(ConsumerRecord<String, RetryMessage> recordEvent) {
		RetryMessage message = recordEvent.value();
		MissionRetryer missionRetryer = missionRetryerFactory.get(message.retryStep());
		missionRetryer.process(recordEvent.key(), message);
	}
}
