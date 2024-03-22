package univ.earthbreaker.namu.database.core.mission;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import univ.earthbreaker.namu.core.domain.mission.Mission;
import univ.earthbreaker.namu.core.domain.mission.MissionStatus;
import univ.earthbreaker.namu.core.domain.mission.MissionType;
import univ.earthbreaker.namu.database.core.common.BaseTimeJpaEntity;

@Entity
@Table(name = "member_mission")
public class MemberMissionJpaEntity extends BaseTimeJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;

	@Column(nullable = false)
	private Long memberNo;

	@Column(nullable = false)
	private Long missionNo;

	@Column(nullable = false, length = 100)
	private String title;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MissionType type;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MissionStatus status;

	protected MemberMissionJpaEntity() {
	}

	Mission toMission() {
		return new Mission(missionNo, title, type, status);
	}
}
