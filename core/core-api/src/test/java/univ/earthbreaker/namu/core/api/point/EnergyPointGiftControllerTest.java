package univ.earthbreaker.namu.core.api.point;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static univ.earthbreaker.namu.core.domain.point.EnergyPointFixture.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.ResultActions;

import univ.earthbreaker.namu.core.api.PresentationTest;
import univ.earthbreaker.namu.core.domain.point.EnergyGiftCommand;
import univ.earthbreaker.namu.core.domain.point.EnergyPointGiftService;
import univ.earthbreaker.namu.core.domain.point.EnergyPointPushNotificationBridge.GiftResult;
import univ.earthbreaker.namu.external.notification.NotificationAdapter;
import univ.earthbreaker.namu.external.notification.NotificationPort;
import univ.earthbreaker.namu.test.api.ApiDocsUtils;

class EnergyPointGiftControllerTest extends PresentationTest {

	private static final String GIFT_POINT_URI = "/v1/points/gift/{targetMemberNo}";

	private final EnergyPointGiftService energyPointProvideService = Mockito.mock(EnergyPointGiftService.class);
	private final NotificationPort notificationPort = Mockito.mock(NotificationAdapter.class);
	private final EnergyPointGiftController energyPointGiftController
		= new EnergyPointGiftController(energyPointProvideService, notificationPort);

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = mockControllerWithAuthorization(energyPointGiftController);
	}

	@DisplayName("현재 회원의 포인트를 사용해 회원의 친구에게 에너지를 선물하고, status 204 를 반환한다")
	@Test
	void giveEnergyPointToFriend() throws Exception {
		// given
		EnergyGiftRequest request = new EnergyGiftRequest(USE_POINT_VALUE);
		Mockito.when(energyPointProvideService.giftEnergyPointToFriend(Mockito.any(EnergyGiftCommand.class)))
			.thenReturn(new GiftResult("nickname", "targetTokenValue"));

		// when
		ResultActions resultActions = whenPostWithAuthorization(GIFT_POINT_URI, FRIEND_NO, request);

		// then
		resultActions.andExpect(status().isNoContent());

		// apidocs
		resultActions
			.andDo(
				document(
					ApiDocsUtils.API_DOCUMENT_IDENTIFIER,
					ApiDocsUtils.operationRequestPreprocessor(),
					ApiDocsUtils.operationResponsePreprocessor(),
					requestHeaders(
						headerWithName(HttpHeaders.AUTHORIZATION).description("회원의 access 토큰 값")
					)
				)
			);
	}
}
