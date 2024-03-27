package univ.earthbreaker.namu.database.core.point;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EnergyPointJpaRepository extends JpaRepository<EnergyPointJpaEntity, Long> {

	@NotNull EnergyPointJpaEntity findByMemberNo(long memberNo);

	@Modifying
	@Query("UPDATE EnergyPointJpaEntity ep SET ep.point =: point WHERE ep.memberNo =: memberNo")
	void update(long memberNo, int point);
}
