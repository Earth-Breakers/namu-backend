package univ.earthbreaker.namu.core.api.point;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static univ.earthbreaker.namu.core.domain.point.EnergyPointFixture.FRIEND_NO;
import static univ.earthbreaker.namu.core.domain.point.EnergyPointFixture.USE_POINT_VALUE;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.API_DOCUMENT_IDENTIFIER;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.operationRequestPreprocessor;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.operationResponsePreprocessor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.ResultActions;

import univ.earthbreaker.namu.core.api.PresentationTest;
import univ.earthbreaker.namu.core.domain.point.EnergyPointGiftService;

class EnergyPointGiftControllerTest extends PresentationTest {

	private static final String GIFT_POINT_URI = "/v1/points/gift/{targetMemberNo}";

	private final EnergyPointGiftService energyPointProvideService = Mockito.mock(EnergyPointGiftService.class);
	private final EnergyPointGiftController energyPointGiftController = new EnergyPointGiftController(energyPointProvideService);

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = mockControllerWithAuthorization(energyPointGiftController);
	}

	@DisplayName("현재 회원의 포인트를 사용해 회원의 친구에게 에너지를 선물하고, status 204 를 반환한다")
	@Test
	void giveEnergyPointToFriend() throws Exception {
		// given
		EnergyGiftRequest request = new EnergyGiftRequest(USE_POINT_VALUE);

		// when
		ResultActions resultActions = whenPostWithAuthorization(GIFT_POINT_URI, FRIEND_NO, request);

		// then
		resultActions.andExpect(status().isNoContent());

		// apidocs
		resultActions
			.andDo(
				document(
					API_DOCUMENT_IDENTIFIER,
					operationRequestPreprocessor(),
					operationResponsePreprocessor(),
					requestHeaders(
						headerWithName(HttpHeaders.AUTHORIZATION).description("회원의 access 토큰 값")
					)
				)
			);
	}
}
