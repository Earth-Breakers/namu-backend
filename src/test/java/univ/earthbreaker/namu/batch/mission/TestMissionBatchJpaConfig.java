package univ.earthbreaker.namu.batch.mission;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import univ.earthbreaker.namu.database.core.member.MemberJpaEntity;
import univ.earthbreaker.namu.database.core.mission.FixMissionJpaEntity;
import univ.earthbreaker.namu.database.core.mission.FixMissionJpaRepository;
import univ.earthbreaker.namu.database.core.mission.MemberMissionJpaEntity;
import univ.earthbreaker.namu.database.core.mission.MemberMissionJpaRepository;

@TestConfiguration
@EnableJpaRepositories(basePackageClasses = {FixMissionJpaRepository.class, MemberMissionJpaRepository.class})
@EntityScan(basePackageClasses = {MemberJpaEntity.class, FixMissionJpaEntity.class, MemberMissionJpaEntity.class})
public class TestMissionBatchJpaConfig {

	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
			.setType(EmbeddedDatabaseType.H2)
			.addScript("/org/springframework/batch/core/schema-h2.sql")
			.build();
	}
}
