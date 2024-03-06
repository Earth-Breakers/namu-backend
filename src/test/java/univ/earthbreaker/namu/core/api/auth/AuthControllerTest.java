package univ.earthbreaker.namu.core.api.auth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static univ.earthbreaker.namu.core.api.auth.AuthApiFixture.ACCESS_TOKEN;
import static univ.earthbreaker.namu.core.api.auth.AuthApiFixture.ALREADY_MEMBER_LOGIN_RESULT;
import static univ.earthbreaker.namu.core.api.auth.AuthApiFixture.LOGIN_REQUEST;
import static univ.earthbreaker.namu.core.api.auth.AuthApiFixture.NEW_MEMBER_LOGIN_RESULT;
import static univ.earthbreaker.namu.core.api.auth.AuthApiFixture.O_AUTH_RESULT;
import static univ.earthbreaker.namu.core.api.auth.AuthApiFixture.REFRESH_TOKEN;
import static univ.earthbreaker.namu.core.api.auth.AuthApiFixture.SOCIAL_TOKEN;
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
import univ.earthbreaker.namu.core.api.auth.support.HttpHeaderUtils;
import univ.earthbreaker.namu.core.domain.account.AccountService;
import univ.earthbreaker.namu.core.domain.account.LoginCommand;
import univ.earthbreaker.namu.external.oauth.OAuthClientApi;
import univ.earthbreaker.namu.external.oauth.OAuthClientException;

class AuthControllerTest extends PresentationTest {

	private static final String LOGIN_URI = "/v1/auth/login/kakao";

	private final OAuthClientApi oAuthClientApi = Mockito.mock(OAuthClientApi.class);
	private final AccountService accountService = Mockito.mock(AccountService.class);
	private final AuthController authController = new AuthController(oAuthClientApi, accountService);

	@BeforeEach
	void setUp() {
		mockMvc = mockController(authController);
	}

	@DisplayName("처음면 로그인 하는 회원이 oAuth 로그인을 하면 회원 가입을 하고 status 201과 accessToken, refreshToken 을 반환한다")
	@Test
	void join_loginOrJoin() throws Exception {
		// given
		Mockito.when(oAuthClientApi.getOAuthMemberInfo(SOCIAL_TOKEN))
			.thenReturn(O_AUTH_RESULT);
		Mockito.when(accountService.loginOrJoin(Mockito.any(LoginCommand.class)))
			.thenReturn(NEW_MEMBER_LOGIN_RESULT);

		// when
		ResultActions resultActions = whenPost(LOGIN_URI, LOGIN_REQUEST);

		// then
		resultActions
			.andExpect(status().isCreated())
			.andExpect(header().string(HttpHeaders.AUTHORIZATION, "Bearer " + ACCESS_TOKEN))
			.andExpect(header().string(HttpHeaderUtils.REFRESH_TOKEN, REFRESH_TOKEN));

		// apidocs
		resultActions.andDo(
			document(
				API_DOCUMENT_IDENTIFIER,
				operationRequestPreprocessor(),
				operationResponsePreprocessor(),
				requestFields(
					fieldWithPath("socialToken").type(JsonFieldType.STRING).description("사용자의 카카오 소셜 토큰 값"),
					fieldWithPath("notificationToken").type(JsonFieldType.STRING).description("사용자 푸시 알림 용 디바이스 토큰 값")
				),
				responseHeaders(
					headerWithName(HttpHeaders.AUTHORIZATION).description("토큰 유형과 회원의 access 토큰 값"),
					headerWithName(HttpHeaderUtils.REFRESH_TOKEN).description("회원의 refresh 토큰 값")
				)
			)
		);
	}

	@DisplayName("기존 회원이 oAuth 로그인을 하면 로그인을 하고 status 200과 accessToken, refreshToken 을 반환한다")
	@Test
	void login_loginOrJoin() throws Exception {
		// given
		Mockito.when(oAuthClientApi.getOAuthMemberInfo(SOCIAL_TOKEN))
			.thenReturn(O_AUTH_RESULT);
		Mockito.when(accountService.loginOrJoin(Mockito.any(LoginCommand.class)))
			.thenReturn(ALREADY_MEMBER_LOGIN_RESULT);

		// when
		ResultActions resultActions = whenPost(LOGIN_URI, LOGIN_REQUEST);

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(header().string(HttpHeaders.AUTHORIZATION, "Bearer " + ACCESS_TOKEN))
			.andExpect(header().string(HttpHeaderUtils.REFRESH_TOKEN, REFRESH_TOKEN));

		// apidocs
		resultActions.andDo(
			document(
				API_DOCUMENT_IDENTIFIER,
				operationRequestPreprocessor(),
				operationResponsePreprocessor(),
				requestFields(
					fieldWithPath("socialToken").type(JsonFieldType.STRING).description("사용자의 카카오 소셜 토큰 값"),
					fieldWithPath("notificationToken").type(JsonFieldType.STRING).description("사용자 푸시 알림 용 디바이스 토큰 값")
				),
				responseHeaders(
					headerWithName(HttpHeaders.AUTHORIZATION).description("토큰 유형과 회원의 access 토큰 값"),
					headerWithName(HttpHeaderUtils.REFRESH_TOKEN).description("회원의 refresh 토큰 값")
				)
			)
		);
	}

	@DisplayName("소셜 로그인을 위한 oauth client api 요청 시 알 수 없는 에러가 발생하면 예외를 발생시키고 401 을 반환한다")
	@Test
	void fail_oauth_join() throws Exception {
		// given
		Mockito.when(oAuthClientApi.getOAuthMemberInfo(SOCIAL_TOKEN))
			.thenThrow(OAuthClientException.another());

		// when
		ResultActions resultActions = whenPost(LOGIN_URI, LOGIN_REQUEST);

		// then, apidocs
		resultActions
			.andExpect(status().isUnauthorized())
			.andExpect(result ->
				assertThat(result.getResolvedException())
					.isInstanceOf(OAuthClientException.class)
					.hasMessage(OAuthClientException.another().getMessage())
			)
			.andDo(commonExceptionDocumentResultHandler());
	}

	@DisplayName("소셜 로그인을 위한 oauth client api 요청 시 소셜 토큰이 만료되면 예외를 발생시키고 401 을 반환한다")
	@Test
	void fail_oauth_unauthorized_join() throws Exception {
		// given
		Mockito.when(oAuthClientApi.getOAuthMemberInfo(SOCIAL_TOKEN))
			.thenThrow(OAuthClientException.unauthorized());

		// when
		ResultActions resultActions = whenPost(LOGIN_URI, LOGIN_REQUEST);

		// then, apidocs
		resultActions
			.andExpect(status().isUnauthorized())
			.andExpect(result ->
				assertThat(result.getResolvedException())
					.isInstanceOf(OAuthClientException.class)
					.hasMessage(OAuthClientException.unauthorized().getMessage())
			)
			.andDo(commonExceptionDocumentResultHandler());
	}
}
