package univ.earthbreaker.namu.core.domain.mission;

import java.util.Objects;

public class MemberMission {

	private final long no;
	private final long memberNo;
	private final String title;
	private final MissionType type;
	private final MissionStatus status;

	public MemberMission(long no, long memberNo, String title, MissionType type, MissionStatus status) {
		this.no = no;
		this.memberNo = memberNo;
		this.title = title;
		this.type = type;
		this.status = status;
	}

	MemberMission process() {
		return new MemberMission(no, memberNo, title, type, MissionStatus.IN_PROGRESS);
	}

	MemberMission failure() {
		return new MemberMission(no, memberNo, title, type, MissionStatus.FAILURE);
	}

	MemberMission success() {
		return new MemberMission(no, memberNo, title, type, MissionStatus.SUCCESS);
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

	public long getMemberNo() {
		return memberNo;
	}

	public String getTitle() {
		return title;
	}

	public MissionStatus getStatus() {
		return status;
	}

	int getRewardPoint() {
		return type.getPoint();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MemberMission mission = (MemberMission)o;
		return no == mission.no && title.equals(mission.title) && type == mission.type && status == mission.status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(no, title, type, status);
	}
}
