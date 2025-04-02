package univ.earthbreaker.namu.core.api.mission;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static univ.earthbreaker.namu.core.domain.mission.MissionFixture.*;
import static univ.earthbreaker.namu.core.domain.mission.MissionStatus.READY;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import univ.earthbreaker.namu.test.api.ApiDocsUtils;

import univ.earthbreaker.namu.core.api.PresentationTest;
import univ.earthbreaker.namu.core.domain.mission.MemberMissionQueryService;

class MemberMissionQueryControllerTest extends PresentationTest {

	private static final String RETRIEVE_MISSIONS_URI = "/v1/missions";

	private final MemberMissionQueryService memberMissionQueryService = Mockito.mock(MemberMissionQueryService.class);
	private final MemberMissionQueryController memberMissionQueryController
		= new MemberMissionQueryController(memberMissionQueryService);

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = mockControllerWithAuthorization(memberMissionQueryController);
	}

	@DisplayName("특별한 날짜가 아닌 날짜에 회원이 미션 목록을 조회하면 status 200 과 특별 미션이 포함되지 않은 회원의 미션 목록을 반환한다")
	@Test
	void retrieve_normal_day() throws Exception {
		// given
		Mockito.when(memberMissionQueryService.retrieveMemberMissions(MEMBER_NO))
			.thenReturn(SPECIAL_DAY_RESULT);

		// when
		ResultActions resultActions = whenGetWithAuthorization(RETRIEVE_MISSIONS_URI);

		// then
		resultActions
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.todayMissions").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.todayMissions").isArray())
			.andExpect(MockMvcResultMatchers.jsonPath("$.todayMissions[0].missionNo").value(TODAY_MISSION_NO))
			.andExpect(MockMvcResultMatchers.jsonPath("$.todayMissions[0].title").value(TODAY_MISSION_ACTIVITY_TITLE.name()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.todayMissions[0].status").value(READY.name()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.defaultMissions").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.defaultMissions").isArray())
			.andExpect(MockMvcResultMatchers.jsonPath("$.defaultMissions[0].missionNo").value(DEFAULT_MISSION_NO))
			.andExpect(MockMvcResultMatchers.jsonPath("$.defaultMissions[0].title").value(DEFAULT_MISSION_ACTIVITY_TITLE.name()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.defaultMissions[0].status").value(READY.name()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.specialMissions").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.specialMissions").isArray())
			.andExpect(MockMvcResultMatchers.jsonPath("$.specialMissions").isEmpty());

		// apidocs
		resultActions
			.andDo(
				document(
					ApiDocsUtils.API_DOCUMENT_IDENTIFIER,
					ApiDocsUtils.operationRequestPreprocessor(),
					ApiDocsUtils.operationResponsePreprocessor(),
					requestHeaders(
						headerWithName(HttpHeaders.AUTHORIZATION).description("회원의 access 토큰 값")
					),
					responseFields(
						fieldWithPath("todayMissions[]").type(ARRAY).description("오늘의 미션 목록"),
						fieldWithPath("todayMissions[].missionNo").type(NUMBER).description("오늘의 미션 번호"),
						fieldWithPath("todayMissions[].title").type(STRING).description("오늘의 미션 제목"),
						fieldWithPath("todayMissions[].status").type(STRING).description("오늘의 미션 상태"),
						fieldWithPath("defaultMissions[]").type(ARRAY).description("기본 미션 목록"),
						fieldWithPath("defaultMissions[].missionNo").type(NUMBER).description("기본 미션 번호"),
						fieldWithPath("defaultMissions[].title").type(STRING).description("기본 미션 제목"),
						fieldWithPath("defaultMissions[].status").type(STRING).description("기본 미션 상태"),
						fieldWithPath("specialMissions").type(ARRAY).description("특별 미션 목록")
					)
				)
			);
	}

	@DisplayName("특별한 날짜에 회원이 미션 목록을 조회하면 status 200 과 특별 미션이 포함된 회원의 미션 목록을 반환한다")
	@Test
	void retrieve_special_day() throws Exception {
		// given
		Mockito.when(memberMissionQueryService.retrieveMemberMissions(MEMBER_NO))
			.thenReturn(NORMAL_DAY_RESULT);

		// when
		ResultActions resultActions = whenGetWithAuthorization(RETRIEVE_MISSIONS_URI);

		// then
		resultActions
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.todayMissions").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.todayMissions").isArray())
			.andExpect(MockMvcResultMatchers.jsonPath("$.todayMissions[0].missionNo").value(TODAY_MISSION_NO))
			.andExpect(MockMvcResultMatchers.jsonPath("$.todayMissions[0].title").value(TODAY_MISSION_ACTIVITY_TITLE.name()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.todayMissions[0].status").value(READY.name()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.defaultMissions").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.defaultMissions").isArray())
			.andExpect(MockMvcResultMatchers.jsonPath("$.defaultMissions[0].missionNo").value(DEFAULT_MISSION_NO))
			.andExpect(MockMvcResultMatchers.jsonPath("$.defaultMissions[0].title").value(DEFAULT_MISSION_ACTIVITY_TITLE.name()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.defaultMissions[0].status").value(READY.name()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.specialMissions").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.specialMissions").isArray())
			.andExpect(MockMvcResultMatchers.jsonPath("$.specialMissions[0].missionNo").value(SPECIAL_MISSION_NO))
			.andExpect(MockMvcResultMatchers.jsonPath("$.specialMissions[0].title").value(SPECIAL_MISSION_ACTIVITY_TITLE.name()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.specialMissions[0].status").value(READY.name()));

		// apidocs
		resultActions
			.andDo(
				document(
					ApiDocsUtils.API_DOCUMENT_IDENTIFIER,
					ApiDocsUtils.operationRequestPreprocessor(),
					ApiDocsUtils.operationResponsePreprocessor(),
					requestHeaders(
						headerWithName(HttpHeaders.AUTHORIZATION).description("회원의 access 토큰 값")
					),
					responseFields(
						fieldWithPath("todayMissions[]").type(ARRAY).description("오늘의 미션 목록"),
						fieldWithPath("todayMissions[].missionNo").type(NUMBER).description("오늘의 미션 번호"),
						fieldWithPath("todayMissions[].title").type(STRING).description("오늘의 미션 제목"),
						fieldWithPath("todayMissions[].status").type(STRING).description("오늘의 미션 상태"),
						fieldWithPath("defaultMissions[]").type(ARRAY).description("기본 미션 목록"),
						fieldWithPath("defaultMissions[].missionNo").type(NUMBER).description("기본 미션 번호"),
						fieldWithPath("defaultMissions[].title").type(STRING).description("기본 미션 제목"),
						fieldWithPath("defaultMissions[].status").type(STRING).description("기본 미션 상태"),
						fieldWithPath("specialMissions").type(ARRAY).description("특별 미션 목록"),
						fieldWithPath("specialMissions[].missionNo").type(NUMBER).description("특별 미션 번호"),
						fieldWithPath("specialMissions[].title").type(STRING).description("특별 미션 제목"),
						fieldWithPath("specialMissions[].status").type(STRING).description("특별 미션 상태")
					)
				)
			);
	}
}
