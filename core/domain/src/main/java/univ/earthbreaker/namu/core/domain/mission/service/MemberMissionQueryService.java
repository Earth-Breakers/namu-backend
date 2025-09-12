package univ.earthbreaker.namu.core.domain.mission.service;

import org.springframework.stereotype.Service;

import univ.earthbreaker.namu.core.domain.mission.MemberMissions;

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
		MemberMissions memberMissions = memberMissionFinder.findAll(memberNo);
		MissionRetrieveStrategy missionRetrieveStrategy = missionRetrieveStrategyFactory.get();
		return missionRetrieveStrategy.retrieve(memberMissions);
	}
}
