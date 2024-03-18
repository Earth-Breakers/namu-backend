package univ.earthbreaker.namu.core.api.energy;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.API_DOCUMENT_IDENTIFIER;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.operationRequestPreprocessor;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.operationResponsePreprocessor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import univ.earthbreaker.namu.core.api.PresentationTest;
import univ.earthbreaker.namu.core.domain.energy.EnergyGiftCommand;
import univ.earthbreaker.namu.core.domain.energy.EnergyGiftService;

class EnergyGiftControllerTest extends PresentationTest {

	private static final String GIFT_URI = "/v1/energy/gift/{friendNo}";
	private static final long FRIEND_MEMBER_NO = 2L;
	private static final int GIFT_POINT = 100;

	private final EnergyGiftService energyGiftService = Mockito.mock(EnergyGiftService.class);
	private final EnergyGiftController energyGiftController = new EnergyGiftController(energyGiftService);

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = mockControllerWithAuthorization(energyGiftController);
	}

	@DisplayName("")
	@Test
	void success_giftToFriend() throws Exception {
	    // given
		GiftRequest request = new GiftRequest(GIFT_POINT);

		// when
		ResultActions resultActions = whenPostWithAuthorization(GIFT_URI, FRIEND_MEMBER_NO, request);

		// then
		resultActions.andExpect(status().isOk());

		// apidocs
		resultActions.andDo(
			document(
				API_DOCUMENT_IDENTIFIER,
				operationRequestPreprocessor(),
				operationResponsePreprocessor(),
				requestHeaders(
					headerWithName(HttpHeaders.AUTHORIZATION).description("회원의 access 토큰 값")
				),
				pathParameters(
					parameterWithName("friendNo").description("선물을 보낼 회원의 번호")
				),
				requestFields(
					fieldWithPath("giftPoint").type(JsonFieldType.NUMBER).description("선물할 포인트")
				)
			)
		);
	}

	@DisplayName("")
	@Test
	void fail_giftToFriend_point_lack() throws Exception {
		// given
		Mockito.doThrow(new IllegalArgumentException("포인트가 없어 선물할 수 없습니다"))
			.when(energyGiftService)
			.givePointToFriend(Mockito.any(EnergyGiftCommand.class));
		GiftRequest request = new GiftRequest(GIFT_POINT);

		// when
		ResultActions resultActions = whenPostWithAuthorization(GIFT_URI, FRIEND_MEMBER_NO, request);

		// then, apidocs
		resultActions
			.andExpect(status().isBadRequest())
			.andExpect(result ->
				assertThat(result.getResolvedException())
					.isInstanceOf(IllegalArgumentException.class)
					.hasMessage("포인트가 없어 선물할 수 없습니다")
			)
			.andDo(commonExceptionDocumentResultHandler());
	}


	// "선물할 수 있는 최대 허용 포인트를 초과했습니다"
	@DisplayName("")
	@Test
	void fail_giftToFriend_point_overflow() throws Exception {
		// given
		Mockito.doThrow(new IllegalArgumentException("선물할 수 있는 최대 허용 포인트를 초과했습니다"))
			.when(energyGiftService)
			.givePointToFriend(Mockito.any(EnergyGiftCommand.class));
		GiftRequest request = new GiftRequest(GIFT_POINT);

		// when
		ResultActions resultActions = whenPostWithAuthorization(GIFT_URI, FRIEND_MEMBER_NO, request);

		// then, apidocs
		resultActions
			.andExpect(status().isBadRequest())
			.andExpect(result ->
				assertThat(result.getResolvedException())
					.isInstanceOf(IllegalArgumentException.class)
					.hasMessage("선물할 수 있는 최대 허용 포인트를 초과했습니다")
			)
			.andDo(commonExceptionDocumentResultHandler());
	}
}
