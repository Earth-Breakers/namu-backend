package univ.earthbreaker.namu.core.domain.member;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository {

	@Nullable Member findMemberNoOrNull(long memberNo);

	@NotNull Long create(String socialNickname);
}
