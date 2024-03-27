package univ.earthbreaker.namu.database.core.mission;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import univ.earthbreaker.namu.core.domain.mission.MissionStatus;

public interface MemberMissionJpaRepository extends JpaRepository<MemberMissionJpaEntity, Long> {

	List<MemberMissionJpaEntity> findAllByMemberNo(long memberNo);

	@NotNull MemberMissionJpaEntity findByMemberNoAndMissionNo(long memberNo, long missionNo);

	@Modifying
	@Query("UPDATE MemberMissionJpaEntity mm SET mm.status =: status WHERE mm.missionNo =: missionNo AND mm.memberNo =: memberNo")
	void updateMemberMission(long memberNo, long missionNo, MissionStatus status);
}
