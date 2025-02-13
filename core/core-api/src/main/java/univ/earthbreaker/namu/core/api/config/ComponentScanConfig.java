package univ.earthbreaker.namu.core.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
	"univ.earthbreaker.namu.external.aws",
	"univ.earthbreaker.namu.external.oauth",
	"univ.earthbreaker.namu.external.notification"
})
public class ComponentScanConfig {
}
