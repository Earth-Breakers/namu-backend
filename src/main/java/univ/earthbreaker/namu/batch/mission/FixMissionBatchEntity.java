package univ.earthbreaker.namu.batch.mission;

import java.io.Serializable;

public record FixMissionBatchEntity(
	Long missionNo,
	String missionActivity,
	String missionType
) implements Serializable {
}
