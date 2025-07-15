package univ.earthbreaker.namu.core.storage.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfiguration implements SeparatedProfileDataSourceConfigurable<DataSource> {

	private static final String DATA_SOURCE_BEAN = "dataSource";

	@Override
	@Primary
	@Bean(DATA_SOURCE_BEAN)
	@Profile({"local"})
	public DataSource embedded() {
		return H2Factory.embeddedDatabaseFactory().getDatabase();
	}

	@Override
	@Primary
	@Bean(DATA_SOURCE_BEAN)
	@Profile({"local-dev", "dev"})
	public DataSource remote(@Qualifier("hikariConfig") HikariConfig config) {
		return new HikariDataSource(config);
	}

	@Bean
	@ConfigurationProperties(prefix = "database.datasource.core")
	@Profile({"local-dev", "dev"})
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
}
