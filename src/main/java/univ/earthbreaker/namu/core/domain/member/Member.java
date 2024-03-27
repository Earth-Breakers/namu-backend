package univ.earthbreaker.namu.core.domain.member;

public class Member {

	private final long no;
	private final String nickname;
	private final int level;

	/**
	 * FIXME : 회원 레벨 및 경험치 관리 도메인 개발 필요
	 */
	private int requiredExp;
	private int currentExp;
	private int accumulateExp;

	private final MemberStatus status;

	public Member(long no, String nickname, int level, MemberStatus status) {
		this.no = no;
		this.nickname = nickname;
		this.level = level;
		this.status = status;
	}

	public Member(long no, String nickname, int level, int requiredExp, int currentExp, int accumulateExp,
		MemberStatus status) {
		this.no = no;
		this.nickname = nickname;
		this.level = level;
		this.requiredExp = requiredExp;
		this.currentExp = currentExp;
		this.accumulateExp = accumulateExp;
		this.status = status;
	}

	String getNickname() {
		return nickname;
	}

	public long getNo() {
		return no;
	}

	public int getLevel() {
		return level;
	}

	public int getRequiredExp() {
		return requiredExp;
	}

	public int getCurrentExp() {
		return currentExp;
	}

	public int getAccumulateExp() {
		return accumulateExp;
	}
}
