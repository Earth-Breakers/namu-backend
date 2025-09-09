package univ.earthbreaker.namu.clients.point;

import java.util.concurrent.TimeUnit;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Request;
import feign.Retryer;
import feign.codec.ErrorDecoder;

@EnableFeignClients
@Configuration
class PointFeignConfiguration {

	private static final long CONNECTION_TIMEOUT = 1_000;
	private static final long READ_TIMEOUT = 5_000;

	@Bean(name = "feignRequestTimeoutOptions")
	Request.Options requestOptions() {
		return new Request.Options(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS, READ_TIMEOUT, TimeUnit.MILLISECONDS, false);
	}

	@Bean(name = "pointRetryer")
	public Retryer retryer() {
		return Retryer.NEVER_RETRY;
	}

	@Bean(name = "pointErrorDecoder")
	ErrorDecoder errorDecoder() {
		return new PointFeignExceptionDecoder();
	}
}
