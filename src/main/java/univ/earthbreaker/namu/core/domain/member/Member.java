package univ.earthbreaker.namu.core.domain.member;

import java.util.Objects;

public class Member {

	private final Long no;
	private final String nickname;
	private final Integer level;
	private final MemberStatus status;

	public Member(Long no, String nickname, Integer level, MemberStatus status) {
		this.no = no;
		this.nickname = nickname;
		this.level = level;
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Member member = (Member)o;
		return no.equals(member.no) && nickname.equals(member.nickname) && level.equals(member.level)
			&& status == member.status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(no, nickname, level, status);
	}
}
