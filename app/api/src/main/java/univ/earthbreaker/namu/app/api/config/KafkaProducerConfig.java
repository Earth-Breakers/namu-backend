package univ.earthbreaker.namu.app.api.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.StringSerializer;
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
	public KafkaAdmin.NewTopics topics() {
		return new KafkaAdmin.NewTopics(
			TopicBuilder.name("earthbreaker.namu.mission-retry")
				.partitions(10)
				.replicas(3)
				.build()
		);
	}
}
