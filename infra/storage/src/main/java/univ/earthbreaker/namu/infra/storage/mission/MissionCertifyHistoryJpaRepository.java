package univ.earthbreaker.namu.infra.storage.mission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import univ.earthbreaker.namu.core.domain.mission.MissionCertifyProcess;

public interface MissionCertifyHistoryJpaRepository extends JpaRepository<MissionCertifyHistoryJpaEntity, String> {

	MissionCertifyHistoryJpaEntity findByRequestId(String requestId);

	@Modifying
	@Query("UPDATE MissionCertifyHistoryJpaEntity mch SET mch.process = :process WHERE mch.requestId = :requestId")
	void updateHistory(String requestId, MissionCertifyProcess process);
}
