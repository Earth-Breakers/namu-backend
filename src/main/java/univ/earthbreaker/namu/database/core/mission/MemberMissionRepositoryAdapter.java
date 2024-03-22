package univ.earthbreaker.namu.database.core.mission;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import univ.earthbreaker.namu.core.domain.mission.MemberMission;
import univ.earthbreaker.namu.core.domain.mission.MemberMissionRepository;
import univ.earthbreaker.namu.core.domain.mission.Mission;

@Repository
public class MemberMissionRepositoryAdapter implements MemberMissionRepository {

	private final MemberMissionJpaRepository memberMissionJpaRepository;

	public MemberMissionRepositoryAdapter(MemberMissionJpaRepository memberMissionJpaRepository) {
		this.memberMissionJpaRepository = memberMissionJpaRepository;
	}

	@Override
	public @NotNull MemberMission find(long memberNo) {
		List<Mission> missions = memberMissionJpaRepository.findAllByMemberNo(memberNo)
			.stream()
			.map(MemberMissionJpaEntity::toMission)
			.toList();
		return MemberMission.create(memberNo, missions);
	}
}
