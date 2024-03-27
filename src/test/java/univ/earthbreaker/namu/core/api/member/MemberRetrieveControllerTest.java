package univ.earthbreaker.namu.core.api.member;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static univ.earthbreaker.namu.core.domain.member.MemberFixture.*;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import univ.earthbreaker.namu.core.api.PresentationTest;
import univ.earthbreaker.namu.core.domain.member.MemberRetrieveService;

class MemberRetrieveControllerTest extends PresentationTest {

	private static final String HOME_MEMBER_URI = "/v1/members/home";

	private final MemberRetrieveService memberRetrieveService = Mockito.mock(MemberRetrieveService.class);
	private final MemberRetrieveController memberRetrieveController = new MemberRetrieveController(memberRetrieveService);

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = mockControllerWithAuthorization(memberRetrieveController);
	}

	@DisplayName("회원이 현재 보유하고 있는 에너지 포인트를 조회한다")
	@Test
	void retrieve() throws Exception {
		// given
		Mockito.when(memberRetrieveService.retrieve(MEMBER_NO))
			.thenReturn(_MEMBER);

		// when
		ResultActions resultActions = whenGetWithAuthorization(HOME_MEMBER_URI);

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.memberNo").value(MEMBER_NO))
			.andExpect(jsonPath("$.level").value(MEMBER_LEVEL))
			.andExpect(jsonPath("$.requiredExp").value(REQUIRED_EXP))
			.andExpect(jsonPath("$.currentExp").value(CURRENT_EXP));

		// apidocs
		resultActions
			.andDo(
				document(
					API_DOCUMENT_IDENTIFIER,
					operationRequestPreprocessor(),
					operationResponsePreprocessor(),
					requestHeaders(
						headerWithName(HttpHeaders.AUTHORIZATION).description("회원의 access 토큰 값")
					),
					responseFields(
						fieldWithPath("memberNo").type(NUMBER).description("회원 번호"),
						fieldWithPath("level").type(NUMBER).description("회원의 현재 레벨"),
						fieldWithPath("requiredExp").type(NUMBER).description("회원의 현재 레벨의 요구 경험치"),
						fieldWithPath("currentExp").type(NUMBER).description("회원의 현재 경험치")
					)
				)
			);
	}
}
