package univ.earthbreaker.namu.app.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import univ.earthbreaker.namu.app.kafka.consumer.RetryMessage;

@Configuration
@EnableKafka
public class KafkaConfig {

	@Bean
	@ConfigurationProperties("spring.kafka")
	public KafkaProperties kafkaProperties() {
		return new KafkaProperties();
	}

	@Bean
	public Map<String, Object> consumerConfigs() {
		HashMap<String, Object> props = new HashMap<>(kafkaProperties().getProperties());
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
		return props;
	}

	@Bean
	public ConsumerFactory<String, RetryMessage> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(
			consumerConfigs(),
			new StringDeserializer(),
			new JsonDeserializer<>(RetryMessage.class)
		);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, RetryMessage> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, RetryMessage> factory
			= new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setConcurrency(3);
		factory.getContainerProperties().setPollTimeout(3000);
		return factory;
	}
}
