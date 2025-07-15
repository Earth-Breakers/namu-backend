package univ.earthbreaker.namu.batch.core.mission;

import javax.sql.DataSource;

import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import univ.earthbreaker.namu.batch.core.BatchException;

@Configuration
public class MemberPagingItemReaderConfig {

	private final DataSource dataSource;

	public MemberPagingItemReaderConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Bean
	@StepScope
	public JdbcPagingItemReader<MemberBatchEntity> memberItemReader(
		@Value("#{jobParameters[chunkSize]}") Long chunkSize
	) {
		return new JdbcPagingItemReaderBuilder<MemberBatchEntity>()
			.name("memberPagingItemReader")
			.dataSource(dataSource)
			.pageSize(chunkSize.intValue())
			.fetchSize(chunkSize.intValue())
			.queryProvider(pagingQueryProvider())
			.rowMapper(memberBatchEntityRowMapper())
			.build();
	}

	private @NotNull PagingQueryProvider pagingQueryProvider() {
		SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
		queryProvider.setDataSource(dataSource);
		queryProvider.setSelectClause("no, nickname, level, status");
		queryProvider.setFromClause("from member");
		queryProvider.setSortKey("no");
		try {
			PagingQueryProvider provider = queryProvider.getObject();
			Assert.notNull(provider, "paging query provider must not be null");
			return provider;
		} catch (Exception e) {
			throw BatchException.pagingQueryCreationFailed(e.getMessage());
		}
	}

	private @NotNull RowMapper<MemberBatchEntity> memberBatchEntityRowMapper() {
		return (rs, rowNum) -> new MemberBatchEntity(
			rs.getLong("no"),
			rs.getString("nickname"),
			rs.getInt("level"),
			rs.getString("status")
		);
	}
}
