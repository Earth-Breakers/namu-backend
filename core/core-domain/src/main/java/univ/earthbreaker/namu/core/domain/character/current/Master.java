package univ.earthbreaker.namu.core.domain.character.current;

import java.util.Objects;

public class Master {

	private final long memberNo;

	public Master(long memberNo) {
		this.memberNo = memberNo;
	}

	long getMemberNo() {
		return memberNo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Master master = (Master)o;
		return memberNo == master.memberNo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(memberNo);
	}
}
