package univ.earthbreaker.namu.core.domain.mission;

import org.springframework.stereotype.Service;

@Service
public class MemberMissionQueryService {

	private final MemberMissionFinder memberMissionFinder;
	private final MissionRetrieveStrategyFactory missionRetrieveStrategyFactory;

	public MemberMissionQueryService(
		MemberMissionFinder memberMissionFinder,
		MissionRetrieveStrategyFactory missionRetrieveStrategyFactory
	) {
		this.memberMissionFinder = memberMissionFinder;
		this.missionRetrieveStrategyFactory = missionRetrieveStrategyFactory;
	}

	public MemberMissionQueryResult retrieveMemberMissions(long memberNo) {
		MemberMission memberMission = memberMissionFinder.find(memberNo);
		MissionRetrieveStrategy missionRetrieveStrategy = missionRetrieveStrategyFactory.get();
		return missionRetrieveStrategy.retrieve(memberMission);
	}
}
