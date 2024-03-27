package univ.earthbreaker.namu.core.api.point;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.API_DOCUMENT_IDENTIFIER;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.operationRequestPreprocessor;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.operationResponsePreprocessor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import univ.earthbreaker.namu.core.api.PresentationTest;
import univ.earthbreaker.namu.core.domain.point.Energy;
import univ.earthbreaker.namu.core.domain.point.EnergyPointRetrieveService;

class HomePointRetrieveControllerTest extends PresentationTest {

	private static final String HOME_POINT_URI = "/v1/points/home";

	private final EnergyPointRetrieveService pointRetrieveService = Mockito.mock(EnergyPointRetrieveService.class);
	private final HomePointRetrieveController homePointRetrieveController = new HomePointRetrieveController(
		pointRetrieveService);

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = mockControllerWithAuthorization(homePointRetrieveController);
	}

	@DisplayName("회원이 현재 보유하고 있는 에너지 포인트를 조회한다")
	@Test
	void retrieve() throws Exception {
		// given
		long energyNo = 1L;
		long memberNo = 1L;
		int point = 10;
		Mockito.when(pointRetrieveService.retrieve(memberNo))
			.thenReturn(Energy.of(energyNo, memberNo, point));

		// when
		ResultActions resultActions = whenGetWithAuthorization(HOME_POINT_URI);

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.point").value(point));

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
						fieldWithPath("point").type(NUMBER).description("회원이 현재 보유한 에너지 포인트")
					)
				)
			);
	}
}
