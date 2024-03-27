package univ.earthbreaker.namu.database.core.member;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

import univ.earthbreaker.namu.core.domain.member.Member;
import univ.earthbreaker.namu.core.domain.member.MemberRepository;

@Repository
public class MemberJpaRepositoryAdapter implements MemberRepository {

	private final MemberJpaRepository memberJpaRepository;

	public MemberJpaRepositoryAdapter(MemberJpaRepository memberJpaRepository) {
		this.memberJpaRepository = memberJpaRepository;
	}

	@Override
	public @Nullable Member findMemberNoOrNull(long memberNo) {
		return memberJpaRepository.findById(memberNo)
			.map(MemberJpaEntity::toMember)
			.orElse(null);
	}

	@Override
	public @NotNull Long create(String socialNickname) {
		MemberJpaEntity memberJpaEntity = memberJpaRepository.save(MemberJpaEntity.create(socialNickname));
		return memberJpaEntity.getNo();
	}
}
