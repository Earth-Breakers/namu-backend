package univ.earthbreaker.namu.batch.core;

import java.time.Clock;
import java.time.ZoneId;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableBatchProcessing
public class BatchConfig {

	private static final ZoneId KOREA_TIME_ZONE = ZoneId.of("Asia/Seoul");

	@Bean("koreaTimeClockInBatch")
	public Clock clock() {
		return Clock.system(KOREA_TIME_ZONE);
	}
}
