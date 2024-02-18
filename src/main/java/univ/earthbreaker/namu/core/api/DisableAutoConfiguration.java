package univ.earthbreaker.namu.core.api;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = {
	DataSourceAutoConfiguration.class, // jdbc datasource
	SecurityAutoConfiguration.class // Spring Security off
})
public class DisableAutoConfiguration {
}
