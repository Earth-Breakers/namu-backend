package univ.earthbreaker.namu.core.domain.point;

public class Energy {

	private final long no;
	private final long memberNo;
	private final Point point;

	private Energy(long no, long memberNo, Point point) {
		this.no = no;
		this.memberNo = memberNo;
		this.point = point;
	}

	public Energy use(int pointValue) {
		return new Energy(no, memberNo, point.minus(pointValue));
	}

	public Energy receive(int pointValue) {
		return new Energy(no, memberNo, point.plus(pointValue));
	}

	public static Energy of(long no, long memberNo, int pointValue) {
		return new Energy(no, memberNo, new Point(pointValue));
	}

	private record Point(int value) {

		private static final int INITIAL_POINT = 0;

		private Point minus(int point) {
			if (value == INITIAL_POINT) {
				throw new IllegalArgumentException("사용할 수 있는 포인트가 없습니다");
			}
			int minusAfter = value - point;
			if (minusAfter < INITIAL_POINT) {
				throw new IllegalArgumentException("사용할 수 있는 최대 허용 포인트를 초과했습니다");
			}
			return new Point(minusAfter);
		}

		private Point plus(int point) {
			return new Point(value + point);
		}
	}

	public int getPointValue() {
		return point.value;
	}
}
