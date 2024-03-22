package univ.earthbreaker.namu.core.domain.mission;

public enum MissionType {

	DEFAULT("기본 미션", 1),
	TODAY("오늘의 미션", 3),
	SPECIAL("특별 미션", 5),
	;

	private final String value;
	private final int point;

	MissionType(String value, int point) {
		this.value = value;
		this.point = point;
	}

	boolean isDefault() {
		return this == DEFAULT;
	}

	boolean isToday() {
		return this == TODAY;
	}

	boolean isSpecial() {
		return this == SPECIAL;
	}
}
