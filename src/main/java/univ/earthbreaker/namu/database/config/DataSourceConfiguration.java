package univ.earthbreaker.namu.database.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import jakarta.transaction.NotSupportedException;

@Configuration
public class DataSourceConfiguration implements SeparatedProfileDataSourceConfigurable<DataSource> {

	private static final String DATA_SOURCE_BEAN = "dataSourceBean";

	@Override
	@Primary
	@Bean(DATA_SOURCE_BEAN)
	public DataSource embedded() {
		return H2Factory.embeddedDatabaseFactory().getDatabase();
	}

	@Override
	@Primary
	@Bean(DATA_SOURCE_BEAN)
	public DataSource remote() throws NotSupportedException {
		throw new NotSupportedException("not support remote datasource now.");
	}
}
