package univ.earthbreaker.namu.db.core.mission;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import univ.earthbreaker.namu.core.domain.mission.MissionActivity;
import univ.earthbreaker.namu.core.domain.mission.MissionType;
import univ.earthbreaker.namu.db.core.common.BaseTimeJpaEntity;

@Entity
@Table(name = "fix_mission")
public class FixMissionJpaEntity extends BaseTimeJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 100)
	private MissionActivity activity;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MissionType type;

	protected FixMissionJpaEntity() {
	}

	public FixMissionJpaEntity(Long no, String missionActivity, String missionType) {
		this(MissionActivity.valueOf(missionActivity), MissionType.valueOf(missionType));
		this.no = no;
	}

	private FixMissionJpaEntity(MissionActivity activity, MissionType type) {
		this.activity = activity;
		this.type = type;
	}

	MissionActivity getActivity() {
		return activity;
	}

	MissionType getType() {
		return type;
	}

	public Long getMissionNo() {
		return no;
	}

	public String getMissionActivityName() {
		return activity.name();
	}

	public String getMissionTypeName() {
		return type.name();
	}
}
