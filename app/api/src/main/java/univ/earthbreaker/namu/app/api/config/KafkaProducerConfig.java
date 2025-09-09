package univ.earthbreaker.namu.app.api.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@EnableKafka
@Configuration
public class KafkaProducerConfig {

	private final KafkaProperties kafkaProperties;

	public KafkaProducerConfig(KafkaProperties kafkaProperties) {
		this.kafkaProperties = kafkaProperties;
	}

	@Bean
	public ProducerFactory<String, RetryMessage> producerFactory() {
		Map<String, Object> props = new HashMap<>(kafkaProperties.getProperties());
		return new DefaultKafkaProducerFactory<>(props, new StringSerializer(), new JsonSerializer<>());
	}

	@Bean
	public KafkaTemplate<String, RetryMessage> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	@Bean
	public KafkaAdmin.NewTopics topics(
		@Value("${kafka.topics.mission-retry.name}") String retryTopicName,
		@Value("${kafka.topics.mission-retry.partitions}") int retryPartitions,
		@Value("${kafka.topics.mission-retry.replicas}") int retryReplicas
	) {
		return new KafkaAdmin.NewTopics(
			TopicBuilder.name(retryTopicName)
				.partitions(retryPartitions)
				.replicas(retryReplicas)
				.build()
		);
	}

	public record RetryMessage(
		String requestId,
		long memberNo,
		long missionNo,
		String imagePathKey,
		String postContents,
		RetryStep retryStep,
		int attempt
	) {
		public static RetryMessage create(
			String requestId,
			long memberNo,
			long missionNo,
			String imagePathKey,
			String postContents,
			RetryStep retryStep
		) {
			return new RetryMessage(requestId, memberNo, missionNo, imagePathKey, postContents, retryStep, 1);
		}

		public String getKey() {
			return requestId;
		}
	}

	public enum RetryStep {
		IMAGE_UPLOAD,
		POINT_ISSUE,
		;
	}

}
