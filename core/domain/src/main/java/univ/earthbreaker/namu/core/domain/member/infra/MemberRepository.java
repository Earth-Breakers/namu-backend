package univ.earthbreaker.namu.core.domain.member.infra;

import univ.earthbreaker.namu.core.domain.member.Member;

public interface MemberRepository {

	Member findMemberNoOrNull(long memberNo);

	Long create(String socialNickname);
}
