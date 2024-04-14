package univ.earthbreaker.namu.batch.mission;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import univ.earthbreaker.namu.database.core.mission.FixMissionJpaEntity;
import univ.earthbreaker.namu.database.core.mission.FixMissionJpaRepository;
import univ.earthbreaker.namu.database.core.mission.MemberMissionJpaEntity;
import univ.earthbreaker.namu.database.core.mission.MemberMissionJpaRepository;

@Configuration
@EnableJpaRepositories(basePackageClasses = {FixMissionJpaRepository.class, MemberMissionJpaRepository.class})
@EntityScan(basePackageClasses = {FixMissionJpaEntity.class, MemberMissionJpaEntity.class})
public class MissionBatchJpaConfig {
}
