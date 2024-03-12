package univ.earthbreaker.namu.core.domain.friend;

import java.util.Objects;

public class Following {

	private final long followerNo;
	private final int level;
	private final String nickname;

	public Following(long followerNo, int level, String nickname) {
		this.followerNo = followerNo;
		this.level = level;
		this.nickname = nickname;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Following following = (Following)o;
		return followerNo == following.followerNo && level == following.level && nickname.equals(following.nickname);
	}

	@Override
	public int hashCode() {
		return Objects.hash(followerNo, level, nickname);
	}
}
