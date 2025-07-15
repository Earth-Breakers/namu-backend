package univ.earthbreaker.namu.batch.core.mission;

import java.io.Serializable;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.storage.mission.FixMissionJpaEntity;

public record SharedFixMissions(
	List<FixMissionBatchEntity> values
) implements Serializable {

	static @NotNull SharedFixMissions from(@NotNull List<FixMissionJpaEntity> jpaEntities) {
		List<FixMissionBatchEntity> batchEntities = jpaEntities.stream()
			.map(fixMissionJpaEntity -> new FixMissionBatchEntity(
				fixMissionJpaEntity.getMissionNo(),
				fixMissionJpaEntity.getMissionActivityName(),
				fixMissionJpaEntity.getMissionTypeName()
			))
			.toList();
		return new SharedFixMissions(batchEntities);
	}

	List<FixMissionJpaEntity> getJpaEntities() {
		return values.stream()
			.map(fixMissionBatchEntity -> new FixMissionJpaEntity(
				fixMissionBatchEntity.missionNo(),
				fixMissionBatchEntity.missionActivity(),
				fixMissionBatchEntity.missionType()
			))
			.toList();
	}
}
