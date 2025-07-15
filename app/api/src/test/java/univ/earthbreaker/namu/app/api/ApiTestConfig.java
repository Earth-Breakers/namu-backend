package univ.earthbreaker.namu.app.api;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

@TestConfiguration
public class ApiTestConfig {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule localDateModule = new SimpleModule();

		localDateModule.addSerializer(LocalDate.class, new LocalDateSerializer());
		localDateModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
		objectMapper.registerModule(localDateModule);

		return objectMapper;
	}

	static class LocalDateSerializer extends JsonSerializer<LocalDate> {

		@Override
		public void serialize(
			@NotNull LocalDate value,
			@NotNull JsonGenerator gen,
			SerializerProvider serializers
		) throws IOException {
			gen.writeString(value.format(FORMATTER));
		}
	}

	static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

		@Override
		public LocalDate deserialize(@NotNull JsonParser p, DeserializationContext ctxt) throws IOException {
			return LocalDate.parse(p.getValueAsString(), FORMATTER);
		}
	}
}
