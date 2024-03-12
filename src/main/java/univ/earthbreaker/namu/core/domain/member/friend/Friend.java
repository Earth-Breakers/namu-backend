package univ.earthbreaker.namu.core.domain.friend;

import java.util.List;
import java.util.Objects;

public class Friend {

	private final long masterNo;
	private final List<Following> followings;

	public Friend(long masterNo, List<Following> followings) {
		this.masterNo = masterNo;
		this.followings = followings;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Friend friend = (Friend)o;
		return masterNo == friend.masterNo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(masterNo);
	}
}
