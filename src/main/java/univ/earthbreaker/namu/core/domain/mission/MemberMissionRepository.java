package univ.earthbreaker.namu.core.domain.mission;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMissionRepository {

	@NotNull MemberMission find(long memberNo);
}
