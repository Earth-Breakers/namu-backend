package univ.earthbreaker.namu.database.core.mission;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FixMissionJpaRepository extends JpaRepository<FixMissionJpaEntity, Long> {

	@Query("SELECT fm FROM FixMissionJpaEntity fm WHERE fm.type = 'DEFAULT' ORDER BY RAND() LIMIT 5")
	List<FixMissionJpaEntity> findDefaultMissionsByRandom();

	@Query("SELECT fm FROM FixMissionJpaEntity fm WHERE fm.type = 'TODAY' ORDER BY RAND() LIMIT 3")
	List<FixMissionJpaEntity> findTodayMissionsByRandom();

	@Query("SELECT fm FROM FixMissionJpaEntity fm WHERE fm.type = 'SPECIAL'")
	List<FixMissionJpaEntity> findSpecialMissions();
}
