package univ.earthbreaker.namu.infra.storage.mission;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import univ.earthbreaker.namu.core.domain.mission.MissionCertifyProcess;
import univ.earthbreaker.namu.core.domain.mission.MissionCertifyHistory;
import univ.earthbreaker.namu.infra.storage.common.BaseTimeJpaEntity;

@Entity
@Table(name = "mission_certify_history")
public class MissionCertifyHistoryJpaEntity extends BaseTimeJpaEntity {

	@Id
	@Column(name = "request_id")
	private String requestId;

	@Column(name = "member_no", nullable = false)
	private Long memberNo;

	@Column(name = "mission_no", nullable = false)
	private Long missionNo;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 100)
	private MissionCertifyProcess process;

	protected MissionCertifyHistoryJpaEntity() {
	}

	MissionCertifyHistoryJpaEntity(String requestId, Long memberNo, Long missionNo, MissionCertifyProcess process) {
		this.requestId = requestId;
		this.memberNo = memberNo;
		this.missionNo = missionNo;
		this.process = process;
	}

	MissionCertifyHistory toDomainEntity() {
		return new MissionCertifyHistory(requestId, memberNo, missionNo, process);
	}
}
