package univ.earthbreaker.namu.infra.storage.mission;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import univ.earthbreaker.namu.core.domain.mission.MissionStatus;

@Repository
public class MemberMissionBatchRepository {

	private final MemberMissionJpaRepository memberMissionJpaRepository;
	private final SimpleJdbcInsert simpleJdbcInsert;

	public MemberMissionBatchRepository(MemberMissionJpaRepository memberMissionJpaRepository, DataSource dataSource) {
		this.memberMissionJpaRepository = memberMissionJpaRepository;
		this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("member_mission")
			.usingGeneratedKeyColumns("no");
	}

	public void saveAllInBatch(@NotNull List<Long> memberNos, List<FixMissionJpaEntity> fixMissionJpaEntities) {
		SqlParameterSource[] sqlParameterSources = memberNos.stream()
			.map(memberNo -> createSqlParameterSources(memberNo, fixMissionJpaEntities))
			.flatMap(Arrays::stream)
			.toArray(SqlParameterSource[]::new);
		simpleJdbcInsert.executeBatch(sqlParameterSources);
	}

	private MapSqlParameterSource @NotNull [] createSqlParameterSources(
		Long memberNo,
		@NotNull List<FixMissionJpaEntity> fixMissionJpaEntities
	) {
		return fixMissionJpaEntities.stream()
			.map(fixMissionJpaEntity -> createMapSqlParameterSource(memberNo, fixMissionJpaEntity))
			.toArray(MapSqlParameterSource[]::new);
	}

	private @NotNull MapSqlParameterSource createMapSqlParameterSource(
		Long memberNo,
		@NotNull FixMissionJpaEntity fixMissionJpaEntity
	) {
		return new MapSqlParameterSource()
			.addValue("memberNo", memberNo)
			.addValue("missionNo", fixMissionJpaEntity.getMissionNo())
			.addValue("activity", fixMissionJpaEntity.getActivity().name())
			.addValue("type", fixMissionJpaEntity.getType().name())
			.addValue("status", MissionStatus.READY.name())
			.addValue("created_at", LocalDateTime.now(), Types.TIMESTAMP)
			.addValue("updated_at", LocalDateTime.now(), Types.TIMESTAMP);
	}

	public void deleteByMemberNos(List<Long> memberNos) {
		memberMissionJpaRepository.deleteAllByMemberNos(memberNos);
	}
}
