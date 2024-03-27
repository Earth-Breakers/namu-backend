package univ.earthbreaker.namu.core.domain.mission;

import java.util.List;

public class MemberMissions {

	private final List<MemberMission> values;

	MemberMissions(List<MemberMission> values) {
		this.values = values;
	}

	List<MemberMission> findTodayMissions() {
		return values.stream()
			.filter(MemberMission::isToday)
			.toList();
	}

	List<MemberMission> findDefaultMissions() {
		return values.stream()
			.filter(MemberMission::isDefault)
			.toList();
	}

	List<MemberMission> findSpecialMissions() {
		return values.stream()
			.filter(MemberMission::isSpecial)
			.toList();
	}
}
