package univ.earthbreaker.namu.database.core.energy;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;

public interface EnergyPointJpaRepository extends JpaRepository<EnergyPointJpaEntity, Long> {

	@NotNull EnergyPointJpaEntity findByMemberNo(long memberNo);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT ep FROM EnergyPointJpaEntity ep WHERE ep.memberNo IN (:memberNo, :targetMemberNo)")
	List<EnergyPointJpaEntity> findByMemberNos(long memberNo, long targetMemberNo);

	@Modifying
	@Query("UPDATE EnergyPointJpaEntity ep SET ep.point = :pointValue WHERE ep.no = :no")
	void update(long no, int pointValue);
}
