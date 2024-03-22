package univ.earthbreaker.namu.core.domain.mission;

import java.util.List;

import org.jetbrains.annotations.NotNull;

public class MemberMission {

	private final long memberNo;
	private final Missions missions;

	private MemberMission(long memberNo, Missions missions) {
		this.memberNo = memberNo;
		this.missions = missions;
	}

	public static @NotNull MemberMission create(long memberNo, List<Mission> missions) {
		return new MemberMission(memberNo, Missions.from(missions));
	}

	List<Mission> findTodayMissionList() {
		return missions.findTodayMissions();
	}

	List<Mission> findDefaultMissionList() {
		return missions.findDefaultMissions();
	}

	List<Mission> findSpecialMissionList() {
		return missions.findSpecialMissions();
	}
}
