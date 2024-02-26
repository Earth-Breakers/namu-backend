package univ.earthbreaker.namu.external.oauth;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Retryer;
import feign.codec.ErrorDecoder;

@EnableFeignClients
@Configuration
public class KakaoFeignConfiguration {

	@Bean
	Retryer retryer() {
		return new Retryer.Default(500L, 2000L, 3);
	}

	@Bean
	ErrorDecoder errorDecoder() {
		return new KakaoFeignExceptionDecoder();
	}
}
