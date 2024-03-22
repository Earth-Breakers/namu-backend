package univ.earthbreaker.namu.core.domain.mission;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

public class Missions {

	private final Map<Long, Mission> values;

	private Missions(Map<Long, Mission> values) {
		this.values = values;
	}

	static @NotNull Missions from(@NotNull List<Mission> missions) {
		return new Missions(
			missions.stream()
				.collect(Collectors.toMap(
					Mission::getNo,
					mission -> mission
				))
		);
	}

	List<Mission> findTodayMissions() {
		return values.values()
			.stream()
			.filter(Mission::isToday)
			.toList();
	}

	List<Mission> findDefaultMissions() {
		return values.values()
			.stream()
			.filter(Mission::isDefault)
			.toList();
	}

	List<Mission> findSpecialMissions() {
		return values.values()
			.stream()
			.filter(Mission::isSpecial)
			.toList();
	}

	void startMission(long missionNo) {
		values.computeIfPresent(missionNo, (missionNumber, mission) -> mission.process());
	}

	void successMission(long missionNo) {
		values.computeIfPresent(missionNo, (missionNumber, mission) -> mission.success());
	}

	void failMission(long missionNo) {
		values.computeIfPresent(missionNo, (missionNumber, mission) -> mission.failure());
	}
}
