package univ.earthbreaker.namu.core.domain.point;

import org.jetbrains.annotations.NotNull;

public class Energy {

	private final long no;
	private final long memberNo;
	private Point point;

	private Energy(long no, long memberNo, Point point) {
		this.no = no;
		this.memberNo = memberNo;
		this.point = point;
	}

	public static @NotNull Energy of(long no, long memberNo, int pointValue) {
		return new Energy(no, memberNo, new Point(pointValue));
	}

	private record Point(int value) {
	}

	public int getPointValue() {
		return point.value;
	}
}
