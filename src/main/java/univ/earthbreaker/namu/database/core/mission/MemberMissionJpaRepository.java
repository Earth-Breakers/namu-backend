package univ.earthbreaker.namu.database.core.mission;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberMissionJpaRepository extends JpaRepository<MemberMissionJpaEntity, Long> {

	List<MemberMissionJpaEntity> findAllByMemberNo(long memberNo);
}
