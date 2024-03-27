package univ.earthbreaker.namu.core.domain.member;

public class Member {

	private final long no;
	private final String nickname;
	private final int level;
	private final MemberStatus status;

	public Member(long no, String nickname, int level, MemberStatus status) {
		this.no = no;
		this.nickname = nickname;
		this.level = level;
		this.status = status;
	}

	String getNickname() {
		return nickname;
	}
}
