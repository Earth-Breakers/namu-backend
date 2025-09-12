package univ.earthbreaker.namu.infra.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.mission.infra.MissionRetryMessagePublisher;
import univ.earthbreaker.namu.core.domain.mission.infra.RetryMessage;

@Component
public class MissionRetryKafkaMessagePublisher implements MissionRetryMessagePublisher {

	private final KafkaTemplate<String, RetryMessage> kafkaTemplate;
	private final String missionRetryTopic;

	public MissionRetryKafkaMessagePublisher(
		KafkaTemplate<String, RetryMessage> kafkaTemplate,
		@Value("${kafka.topics.mission-retry.name}") String missionRetryTopic
	) {
		this.kafkaTemplate = kafkaTemplate;
		this.missionRetryTopic = missionRetryTopic;
	}

	@Override
	public void publish(RetryMessage message) {
		kafkaTemplate.send(missionRetryTopic, message.getKey(), message);
	}
}
