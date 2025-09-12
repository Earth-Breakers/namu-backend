package univ.earthbreaker.namu.core.domain.mission;

import java.util.Objects;

public class MemberMission {

	private final long no;
	private final long memberNo;
	private final MissionActivity activity;
	private final MissionType type;
	private final MissionStatus status;

	public MemberMission(long no, long memberNo, MissionActivity activity, MissionType type, MissionStatus status) {
		this.no = no;
		this.memberNo = memberNo;
		this.activity = activity;
		this.type = type;
		this.status = status;
	}

	MemberMission process() {
		return new MemberMission(no, memberNo, activity, type, MissionStatus.IN_PROGRESS);
	}

	public MemberMission failure() {
		return new MemberMission(no, memberNo, activity, type, MissionStatus.FAILURE);
	}

	public MemberMission success() {
		return new MemberMission(no, memberNo, activity, type, MissionStatus.SUCCESS);
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

	public String getActivity() {
		return activity.name();
	}

	public MissionStatus getStatus() {
		return status;
	}

	public int getRewardPoint() {
		return type.getPoint();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MemberMission mission = (MemberMission)o;
		return no == mission.no && activity.equals(mission.activity) && type == mission.type && status == mission.status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(no, activity, type, status);
	}
}
