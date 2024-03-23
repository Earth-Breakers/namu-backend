package univ.earthbreaker.namu.database.core.mission;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import univ.earthbreaker.namu.core.domain.mission.MemberMissionRepository;
import univ.earthbreaker.namu.core.domain.mission.MemberMission;

@Repository
public class MemberMissionRepositoryAdapter implements MemberMissionRepository {

	private final MemberMissionJpaRepository memberMissionJpaRepository;

	public MemberMissionRepositoryAdapter(MemberMissionJpaRepository memberMissionJpaRepository) {
		this.memberMissionJpaRepository = memberMissionJpaRepository;
	}

	@Override
	public @NotNull List<MemberMission> findAll(long memberNo) {
		return memberMissionJpaRepository.findAllByMemberNo(memberNo)
			.stream()
			.map(MemberMissionJpaEntity::toMemberMission)
			.toList();
	}
}
