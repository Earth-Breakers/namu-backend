package univ.earthbreaker.namu.app.kafka;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
	"univ.earthbreaker.namu.core.domain",
	"univ.earthbreaker.namu.core.support",
	"univ.earthbreaker.namu.core.storage",
	"univ.earthbreaker.namu.external.image",
	"univ.earthbreaker.namu.clients.point"
})
public class ComponentScanConfig {
}
