package univ.earthbreaker.namu.core.api.friend;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
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
import org.springframework.test.web.servlet.ResultActions;

import univ.earthbreaker.namu.core.api.PresentationTest;
import univ.earthbreaker.namu.core.domain.friend.FriendFollowService;

class FriendControllerTest extends PresentationTest {

	private static final String FOLLOW_URI = "/v1/friend/follow/{targetMemberNo}";

	private final FriendFollowService friendFollowService = Mockito.mock(FriendFollowService.class);
	private final FriendController friendController = new FriendController(friendFollowService);

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = mockControllerWithAuthorization(friendController);
	}

	@DisplayName("팔로우할 상대의 번호를 받아 친구 추가를 하면 204 를 반환한다")
	@Test
	void follow() throws Exception {
	    // given
		Long targetMemberNo = 1L;

	    // when
		ResultActions resultActions = whenPostWithAuthorization(FOLLOW_URI, targetMemberNo);

		// then
		resultActions.andExpect(status().isNoContent());

		// apidocs
		resultActions.andDo(
			document(
				API_DOCUMENT_IDENTIFIER,
				operationRequestPreprocessor(),
				operationResponsePreprocessor(),
				pathParameters(
					parameterWithName("targetMemberNo").description("팔로우할 상대의 번호")
				),
				requestHeaders(
					headerWithName(HttpHeaders.AUTHORIZATION).description("회원의 access 토큰 값")
				)
			)
		);
	}
}
