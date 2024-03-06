package univ.earthbreaker.namu.core.api.auth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static univ.earthbreaker.namu.core.api.auth.AuthApiFixture.ACCESS_TOKEN;
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
import univ.earthbreaker.namu.core.api.auth.support.HttpHeaderUtils;
import univ.earthbreaker.namu.core.auth.TokenReissueService;
import univ.earthbreaker.namu.core.auth.TokenResult;
import univ.earthbreaker.namu.core.auth.UnAuthorizedException;

class ReAuthControllerTest extends PresentationTest {

	private static final String REISSUE_URI = "/v1/auth/reissue";

	private final TokenReissueService tokenReissueService = Mockito.mock(TokenReissueService.class);
	private final ReAuthController reAuthController = new ReAuthController(tokenReissueService);

	@BeforeEach
	void setUp() {
		mockMvc = mockController(reAuthController);
	}

	@DisplayName("회원의 refreshToken 으로 만료된 accessToken 을 재발급 받고, 200 status 를 반환한다")
	@Test
	void reissue() throws Exception {
	    // given
		String refreshToken = AuthApiFixture.REFRESH_TOKEN;
		Mockito.when(tokenReissueService.reissue(refreshToken))
			.thenReturn(new TokenResult(ACCESS_TOKEN));

	    // when
		ResultActions resultActions = whenPost(REISSUE_URI, refreshToken);

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(header().string(HttpHeaders.AUTHORIZATION, "Bearer " + ACCESS_TOKEN));

		// apidocs
		resultActions.andDo(
			document(
				API_DOCUMENT_IDENTIFIER,
				operationRequestPreprocessor(),
				operationResponsePreprocessor(),
				requestHeaders(
					headerWithName(HttpHeaderUtils.REFRESH_TOKEN).description("회원의 refresh 토큰 값")
				),
				responseHeaders(
					headerWithName(HttpHeaders.AUTHORIZATION).description("새로 발급된 access 토큰 값과 토큰 유형")
				)
			)
		);
	}

	@DisplayName("토큰 재발급을 위한 refresh 토큰이 만료되었다면 예외를 발생시키고 401 을 반환한다")
	@Test
	void fail_reissue_expired() throws Exception {
		// given
		String refreshToken = AuthApiFixture.REFRESH_TOKEN;
		Mockito.when(tokenReissueService.reissue(refreshToken))
			.thenThrow(UnAuthorizedException.expired(refreshToken));

		// when
		ResultActions resultActions = whenPost(REISSUE_URI, refreshToken);

		// then, apidocs
		resultActions
			.andExpect(status().isUnauthorized())
			.andExpect(result ->
				assertThat(result.getResolvedException())
					.isInstanceOf(UnAuthorizedException.class)
					.hasMessage(UnAuthorizedException.expired(refreshToken).getMessage())
			)
			.andDo(commonExceptionDocumentResultHandler());
	}

	@DisplayName("토큰 재발급을 위한 refresh 토큰에 해당하는 회원을 찾을 수 없다면 예외를 발생시키고 401 을 반환한다")
	@Test
	void fail_reissue_not_found() throws Exception {
		// given
		String refreshToken = AuthApiFixture.REFRESH_TOKEN;
		Mockito.when(tokenReissueService.reissue(refreshToken))
			.thenThrow(UnAuthorizedException.notFound(refreshToken));

		// when
		ResultActions resultActions = whenPost(REISSUE_URI, refreshToken);

		// then, apidocs
		resultActions
			.andExpect(status().isUnauthorized())
			.andExpect(result ->
				assertThat(result.getResolvedException())
					.isInstanceOf(UnAuthorizedException.class)
					.hasMessage(UnAuthorizedException.notFound(refreshToken).getMessage())
			)
			.andDo(commonExceptionDocumentResultHandler());
	}
}
