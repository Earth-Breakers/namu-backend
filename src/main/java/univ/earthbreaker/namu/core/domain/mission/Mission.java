package univ.earthbreaker.namu.core.domain.mission;

import java.util.Objects;

public class Mission {

	private final long no;
	private final String title;
	private final MissionType type;
	private final MissionStatus status;

	public Mission(long no, String title, MissionType type, MissionStatus status) {
		this.no = no;
		this.title = title;
		this.type = type;
		this.status = status;
	}

	Mission process() {
		return new Mission(no, title, type, MissionStatus.IN_PROGRESS);
	}

	Mission failure() {
		return new Mission(no, title, type, MissionStatus.FAILURE);
	}

	Mission success() {
		return new Mission(no, title, type, MissionStatus.SUCCESS);
	}

	boolean isDefault() {
		return type.isDefault();
	}

	boolean isToday() {
		return type.isToday();
	}

	boolean isSpecial() {
		return type.isSpecial();
	}

	public long getNo() {
		return no;
	}

	public String getTitle() {
		return title;
	}

	public MissionStatus getStatus() {
		return status;
	}

	MissionType getType() {
		return type;
	}

	// record MissionQueryResult(
	// 	long missionNo,
	// 	String title,
	// 	MissionStatus status
	// ) {
	// }

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Mission mission = (Mission)o;
		return no == mission.no && title.equals(mission.title) && type == mission.type && status == mission.status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(no, title, type, status);
	}
}
