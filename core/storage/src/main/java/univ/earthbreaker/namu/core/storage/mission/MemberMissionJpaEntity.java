package univ.earthbreaker.namu.core.storage.mission;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import univ.earthbreaker.namu.core.domain.mission.MemberMission;
import univ.earthbreaker.namu.core.domain.mission.MissionActivity;
import univ.earthbreaker.namu.core.domain.mission.MissionStatus;
import univ.earthbreaker.namu.core.domain.mission.MissionType;
import univ.earthbreaker.namu.core.storage.common.BaseTimeJpaEntity;

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

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 100)
	private MissionActivity activity;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MissionType type;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MissionStatus status;

	protected MemberMissionJpaEntity() {
	}

	MemberMission toMemberMission() {
		return new MemberMission(missionNo, memberNo, activity, type, status);
	}
}
