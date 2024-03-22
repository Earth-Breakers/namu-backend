package univ.earthbreaker.namu.core.domain.mission;

import static univ.earthbreaker.namu.core.domain.mission.MissionRetrieveStrategyFactory.SpecialMissionDate;
import static univ.earthbreaker.namu.core.domain.mission.MissionStatus.READY;
import static univ.earthbreaker.namu.core.domain.mission.MissionType.DEFAULT;
import static univ.earthbreaker.namu.core.domain.mission.MissionType.SPECIAL;
import static univ.earthbreaker.namu.core.domain.mission.MissionType.TODAY;

import java.time.LocalDate;
import java.util.List;

public class MissionFixture {

	public static final long MEMBER_NO = 1;

	public static final long TODAY_MISSION_NO = 1;
	public static final long DEFAULT_MISSION_NO = 2;
	public static final long SPECIAL_MISSION_NO = 3;

	public static final String TODAY_MISSION_TITLE = "todayTitle";
	public static final String DEFAULT_MISSION_TITLE = "defaultTitle";
	public static final String SPECIAL_MISSION_TITLE = "specialTitle";

	public static final Mission TODAY_MISSION_READY = new Mission(TODAY_MISSION_NO, TODAY_MISSION_TITLE, TODAY, READY);
	public static final Mission DEFAULT_MISSION_READY = new Mission(DEFAULT_MISSION_NO, DEFAULT_MISSION_TITLE, DEFAULT, READY);
	public static final Mission SPECIAL_MISSION_READY = new Mission(SPECIAL_MISSION_NO, SPECIAL_MISSION_TITLE, SPECIAL, READY);

	public static final List<Mission> MISSIONS = List.of(TODAY_MISSION_READY, DEFAULT_MISSION_READY, SPECIAL_MISSION_READY);
	public static final MemberMission MEMBER_MISSION = MemberMission.create(MEMBER_NO, MISSIONS);

	public static final LocalDate TREE_PLANTING_DAY = SpecialMissionDate.TREE_PLANTING_DAY.getDate();
	public static final LocalDate BEACH_COMBING_DAY=  SpecialMissionDate.BEACH_COMBING_DAY.getDate();
	public static final LocalDate NORMAL_DAY= LocalDate.of(3333, 1, 2);

	public static final MemberMissionQueryResult NORMAL_DAY_RESULT = new MemberMissionQueryResult(
		List.of(TODAY_MISSION_READY),
		List.of(DEFAULT_MISSION_READY),
		List.of(SPECIAL_MISSION_READY)
	);
	public static final MemberMissionQueryResult SPECIAL_DAY_RESULT = new MemberMissionQueryResult(
		List.of(TODAY_MISSION_READY),
		List.of(DEFAULT_MISSION_READY),
		List.of()
	);
}
