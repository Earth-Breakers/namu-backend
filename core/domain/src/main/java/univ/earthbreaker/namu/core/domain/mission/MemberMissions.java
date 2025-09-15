package univ.earthbreaker.namu.core.domain.mission;

import java.util.List;

public class MemberMissions {

	private final List<MemberMission> values;

	public MemberMissions(List<MemberMission> values) {
		this.values = values;
	}

	public List<MemberMission> findTodayMissions() {
		return values.stream()
			.filter(MemberMission::isToday)
			.toList();
	}

	public List<MemberMission> findDefaultMissions() {
		return values.stream()
			.filter(MemberMission::isDefault)
			.toList();
	}

	public List<MemberMission> findSpecialMissions() {
		return values.stream()
			.filter(MemberMission::isSpecial)
			.toList();
	}
}
