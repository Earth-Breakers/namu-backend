package univ.earthbreaker.namu.app.api.mission;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.app.support.AuthMapping;
import univ.earthbreaker.namu.app.support.LoginMember;
import univ.earthbreaker.namu.core.domain.mission.service.MemberMissionQueryResult;
import univ.earthbreaker.namu.core.domain.mission.service.MemberMissionQueryService;

@RestController
@RequestMapping("/v1/missions")
public class MemberMissionQueryController {

	private final MemberMissionQueryService memberMissionQueryService;

	public MemberMissionQueryController(MemberMissionQueryService memberMissionQueryService) {
		this.memberMissionQueryService = memberMissionQueryService;
	}

	@AuthMapping
	@GetMapping
	public ResponseEntity<MemberMissionResponse> retrieve(@LoginMember Long memberNo) {
		MemberMissionQueryResult missionQueryResult = memberMissionQueryService.retrieveMemberMissions(memberNo);
		return ResponseEntity.ok(MemberMissionResponse.from(missionQueryResult));
	}
}
