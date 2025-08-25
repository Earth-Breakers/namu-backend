package univ.earthbreaker.namu.core.support.retry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@EnableRetry
@Configuration
public class RetryConfiguration {

	@Bean
	public RetryTemplate retryTemplate() {
		RetryTemplate retryTemplate = new RetryTemplate();
		retryTemplate.setBackOffPolicy(new ExponentialBackOffPolicy());
		retryTemplate.setRetryPolicy(new SimpleRetryPolicy(3));
		return retryTemplate;
	}
}
