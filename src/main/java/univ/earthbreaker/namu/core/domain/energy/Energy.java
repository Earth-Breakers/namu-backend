package univ.earthbreaker.namu.core.domain.energy;

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

	void give(int pointValue) {
		this.point = point.minus(pointValue);
	}

	void receive(int pointValue) {
		this.point = point.plus(pointValue);
	}

	public static @NotNull Energy of(long no, long memberNo, int pointValue) {
		return new Energy(no, memberNo, new Point(pointValue));
	}

	private record Point(int value) {

		private static final int INITIAL_POINT = 0;

		private @NotNull Point minus(int point) {
			if (value == INITIAL_POINT) {
				throw new IllegalArgumentException("포인트가 없어 선물할 수 없습니다");
			}
			int minusAfter = value - point;
			if (minusAfter < INITIAL_POINT) {
				throw new IllegalArgumentException("선물할 수 있는 최대 허용 포인트를 초과했습니다");
			}
			return new Point(minusAfter);
		}

		private @NotNull Point plus(int point) {
			return new Point(value + point);
		}
	}

	public long getNo() {
		return no;
	}

	public int getPointValue() {
		return point.value;
	}
}
