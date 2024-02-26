package univ.earthbreaker.namu.core.api.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import univ.earthbreaker.namu.database.DataSourceConfiguration;

@Configuration
@Import({DisableAutoConfiguration.class, DataSourceConfiguration.class})
public class CoreConfiguration {

	public static @NotNull Map<String, Object> getProperties() {
		Map<String, Object> additionalProperties = new ConcurrentHashMap<>();
		additionalProperties.put("spring.config.location", "classpath:/");
		return additionalProperties;
	}
}
