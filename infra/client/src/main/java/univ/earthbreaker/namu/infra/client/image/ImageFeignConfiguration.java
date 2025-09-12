package univ.earthbreaker.namu.infra;

import java.util.concurrent.TimeUnit;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Request;
import feign.Retryer;
import feign.codec.ErrorDecoder;

@EnableFeignClients
@Configuration
public class ImageFeignConfiguration {

	private static final long CONNECTION_TIMEOUT = 3_000;
	private static final long READ_TIMEOUT = 10_000;

	@Bean(name = "imageFeignRequestTimeoutOptions")
	Request.Options requestOptions() {
		return new Request.Options(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS, READ_TIMEOUT, TimeUnit.MILLISECONDS, false);
	}

	@Bean(name = "imageRetryer")
	public Retryer retryer() {
		return Retryer.NEVER_RETRY;
	}

	@Bean(name = "imageErrorDecoder")
	ErrorDecoder errorDecoder() {
		return new ImageFeignExceptionDecoder();
	}
}
