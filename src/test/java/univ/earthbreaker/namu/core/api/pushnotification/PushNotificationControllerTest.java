package univ.earthbreaker.namu.core.api.pushnotification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static univ.earthbreaker.namu.core.domain.pushnotification.PushNotificationFixture.MEMBER_NO;
import static univ.earthbreaker.namu.core.domain.pushnotification.PushNotificationFixture.PUSH_NOTIFICATION_CONSTRUCT_RESULT;
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
import univ.earthbreaker.namu.core.domain.character.CurrentCharacterNotFoundException;
import univ.earthbreaker.namu.core.domain.member.MemberNotFoundException;
import univ.earthbreaker.namu.core.domain.pushnotification.PushNotificationConstructService;
import univ.earthbreaker.namu.external.notification.NotificationPort;

class PushNotificationControllerTest extends PresentationTest {

	private static final String PUSH_NOTIFICATION_TO_ALL = "/v1/push-notification/all";
	private static final String PUSH_NOTIFICATION_TO_FRIENDS = "/v1/push-notification/friends";

	private final PushNotificationConstructService pushNotificationConstructService
		= Mockito.mock(PushNotificationConstructService.class);
	private final NotificationPort notificationPort = Mockito.mock(NotificationPort.class);

	private final PushNotificationController pushNotificationController
		= new PushNotificationController(pushNotificationConstructService, notificationPort);

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = mockControllerWithAuthorization(pushNotificationController);
	}

	@DisplayName("회원 모두에게 자랑하기를 호출하면, 모든 회원에게 자랑 메시지를 전송하고 성공하면 status 200 을 반환한다")
	@Test
	void all_pushNotification() throws Exception {
		// given
		Mockito.when(pushNotificationConstructService.findAllMemberNotificationToken(MEMBER_NO))
			.thenReturn(PUSH_NOTIFICATION_CONSTRUCT_RESULT);

		// when
		ResultActions resultActions = whenPostWithAuthorization(PUSH_NOTIFICATION_TO_ALL);

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
				)
			)
		);
	}

	@DisplayName("회원의 친구에게 자랑하기를 호출하면, 회원의 친구에게만 자랑 메시지를 전송하고 성공하면 status 200 을 반환한다")
	@Test
	void friends_pushNotification() throws Exception {
		// given
		Mockito.when(pushNotificationConstructService.findFriendsNotificationToken(MEMBER_NO))
			.thenReturn(PUSH_NOTIFICATION_CONSTRUCT_RESULT);
		PushNotificationRequest notificationRequest = new PushNotificationRequest("자랑 메시지");

		// when
		ResultActions resultActions = whenPostWithAuthorization(PUSH_NOTIFICATION_TO_FRIENDS, notificationRequest);

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
				requestFields(
					fieldWithPath("content").type(JsonFieldType.STRING).description("친구에게 전송할 자랑 메시지 내용")
				)
			)
		);
	}

	@DisplayName("메시지 전송을 요청한 회원을 찾을 수 없으면 예외를 발생시키고 status 404 를 반환한다")
	@Test
	void fail_pushNotification_member_not_found() throws Exception {
		// given
		Mockito.when(pushNotificationConstructService.findAllMemberNotificationToken(MEMBER_NO))
			.thenThrow(MemberNotFoundException.notFound(MEMBER_NO));

		// when
		ResultActions resultActions = whenPostWithAuthorization(PUSH_NOTIFICATION_TO_ALL);

		// then, apidocs
		resultActions
			.andExpect(status().isNotFound())
			.andExpect(result ->
				assertThat(result.getResolvedException())
					.isInstanceOf(MemberNotFoundException.class)
					.hasMessage(MemberNotFoundException.notFound(MEMBER_NO).getMessage())
			)
			.andDo(commonExceptionDocumentResultHandler());
	}

	@DisplayName("메시지 전송을 요청한 회원의 자랑할 현재 캐릭터를 찾을 수 없으면 예외를 발생시키고 status 404 를 반환한다")
	@Test
	void fail_pushNotification_current_character_not_found() throws Exception {
		// given
		Mockito.when(pushNotificationConstructService.findAllMemberNotificationToken(MEMBER_NO))
			.thenThrow(CurrentCharacterNotFoundException.notFound(MEMBER_NO));

		// when
		ResultActions resultActions = whenPostWithAuthorization(PUSH_NOTIFICATION_TO_ALL);

		// then, apidocs
		resultActions
			.andExpect(status().isNotFound())
			.andExpect(result ->
				assertThat(result.getResolvedException())
					.isInstanceOf(CurrentCharacterNotFoundException.class)
					.hasMessage(CurrentCharacterNotFoundException.notFound(MEMBER_NO).getMessage())
			)
			.andDo(commonExceptionDocumentResultHandler());
	}
}
