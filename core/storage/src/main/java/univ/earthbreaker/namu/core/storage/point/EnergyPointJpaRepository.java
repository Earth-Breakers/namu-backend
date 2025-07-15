package univ.earthbreaker.namu.db.core.point;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EnergyPointJpaRepository extends JpaRepository<EnergyPointJpaEntity, Long> {

	@NotNull EnergyPointJpaEntity findByMemberNo(long memberNo);

	@Modifying
	@Query("UPDATE EnergyPointJpaEntity ep SET ep.point = :point WHERE ep.memberNo = :memberNo")
	void updatePointBy(long memberNo, int point);

	@Modifying
	@Query("UPDATE EnergyPointJpaEntity ep SET ep.point = ep.point + :point WHERE ep.memberNo = :memberNo")
	void updatePointReceive(long memberNo, int point);
}
