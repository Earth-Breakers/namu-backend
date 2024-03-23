package univ.earthbreaker.namu.core.domain.mission;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMissionRepository {

	@NotNull List<MemberMission> findAll(long memberNo);
}
