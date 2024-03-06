package univ.earthbreaker.namu.core.domain.character;

public enum Level {

	BEGIN(1, true),
	MIDDLE(2, true),
	END(3, false),
	;

	private final int value;
	private final boolean growable;

	Level(int value, boolean growable) {
		this.value = value;
		this.growable = growable;
	}

	int up() {
		return value + 1;
	}

	boolean isOverflow(int expectedNextLevel) {
		return expectedNextLevel > END.value;
	}

	boolean isMiddle() {
		return this == MIDDLE;
	}

	boolean isBegin() {
		return this == BEGIN;
	}

	static Level of(int value) {
		return switch (value) {
			case 1 -> BEGIN;
			case 2 -> MIDDLE;
			case 3 -> END;
			default -> throw new IllegalArgumentException("허용하지 않는 level 값입니다");
		};
	}

	int getValue() {
		return value;
	}

	boolean isGrowable() {
		return growable;
	}
}
