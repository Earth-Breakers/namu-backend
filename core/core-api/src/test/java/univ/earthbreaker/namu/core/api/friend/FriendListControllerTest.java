package univ.earthbreaker.namu.core.api.friend;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static univ.earthbreaker.namu.core.domain.member.MemberFixture.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import univ.earthbreaker.namu.core.api.PresentationTest;
import univ.earthbreaker.namu.core.domain.member.friend.FriendFinderService;
import univ.earthbreaker.namu.test.api.ApiDocsUtils;

class FriendListControllerTest extends PresentationTest {

	private static final String FRIEND_LIST_RETRIEVE_T_URI = "/v1/friends/following";

	private final FriendFinderService friendFinderService = Mockito.mock(FriendFinderService.class);
	private final FriendListController friendListController = new FriendListController(friendFinderService);

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = mockControllerWithAuthorization(friendListController);
	}

	@DisplayName("친구가 있는 경우 - 회원 번호를 받아 회원이 팔로잉하는 친구 목록을 조회하고 200을 반환한다")
	@Test
	void retrieveFriendList() throws Exception {
		// given
		Mockito.when(friendFinderService.findMyFriendList(MEMBER_NO))
			.thenReturn(FRIEND_EXIST);

		// when
		ResultActions resultActions = whenGetWithAuthorization(FRIEND_LIST_RETRIEVE_T_URI);

		// then
		resultActions
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.results").isArray())
			.andExpect(MockMvcResultMatchers.jsonPath("$.results").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.results[0].memberNo").value(FOLLOWING_MEMBER_NO))
			.andExpect(MockMvcResultMatchers.jsonPath("$.results[0].nickname").value(FOLLOWING_MEMBER_NICKNAME))
			.andExpect(MockMvcResultMatchers.jsonPath("$.results[0].level").value(FOLLOWING_MEMBER_LEVEL));

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
						fieldWithPath("results[].memberNo").type(NUMBER).description("회원이 팔로잉하는 회원의 번호"),
						fieldWithPath("results[].nickname").type(STRING).description("회원이 팔로잉하는 회원의 닉네임"),
						fieldWithPath("results[].level").type(NUMBER).description("회원이 팔로잉하는 회원의 레벨")
					)
				)
			);
	}

	@DisplayName("친구가 없는 경우 - 회원 번호를 받아 회원이 팔로잉하는 친구 목록을 조회하고 200을 반환한다")
	@Test
	void retrieveFriendList_empty() throws Exception {
		// given
		Mockito.when(friendFinderService.findMyFriendList(MEMBER_NO))
			.thenReturn(FRIEND_NOT_EXIST);

		// when
		ResultActions resultActions = whenGetWithAuthorization(FRIEND_LIST_RETRIEVE_T_URI);

		// then
		resultActions
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.results").isArray())
			.andExpect(MockMvcResultMatchers.jsonPath("$.results").isEmpty());

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
						fieldWithPath("results[]").description("친구가 없으면 빈 배열을 반환합니다")
					)
				)
			);
	}
}
