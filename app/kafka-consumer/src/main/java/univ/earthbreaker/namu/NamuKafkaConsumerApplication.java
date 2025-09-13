package univ.earthbreaker.namu.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
	"univ.earthbreaker.namu.core.domain.mission",
	"univ.earthbreaker.namu.core.service.mission",
	"univ.earthbreaker.namu.infra.storage.mission",
})
public class NamuKafkaConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NamuKafkaConsumerApplication.class, args);
	}
}
