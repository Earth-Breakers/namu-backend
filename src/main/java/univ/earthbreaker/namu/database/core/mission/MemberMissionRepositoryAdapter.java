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

	@Override
	public @NotNull MemberMission find(long memberNo, long missionNo) {
		return memberMissionJpaRepository.findByMemberNoAndMissionNo(memberNo, missionNo)
			.toMemberMission();
	}

	@Override
	public void update(@NotNull MemberMission memberMission) {
		memberMissionJpaRepository.updateMemberMission(
			memberMission.getMemberNo(),
			memberMission.getNo(),
			memberMission.getStatus()
		);
	}
}
