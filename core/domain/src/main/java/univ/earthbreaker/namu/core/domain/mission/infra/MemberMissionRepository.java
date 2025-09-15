package univ.earthbreaker.namu.core.domain.mission.infra;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import univ.earthbreaker.namu.core.domain.mission.MemberMission;

@Repository
public interface MemberMissionRepository {

	@NotNull List<MemberMission> findAll(long memberNo);

	@NotNull MemberMission find(long memberNo, long missionNo);

	void update(MemberMission memberMission);
}
