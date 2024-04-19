package univ.earthbreaker.namu.core.api.post;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static univ.earthbreaker.namu.core.domain.common.Constant.IMAGE_SYSTEM_PROPERTY;
import static univ.earthbreaker.namu.core.domain.post.PostFixture.FIRST_MISSION_NO;
import static univ.earthbreaker.namu.core.domain.post.PostFixture.FIRST_POST;
import static univ.earthbreaker.namu.core.domain.post.PostFixture.POSTS;
import static univ.earthbreaker.namu.core.domain.post.PostFixture.RELATED_POST_FIRST_PAGE_RESULT;
import static univ.earthbreaker.namu.core.domain.post.PostFixture.SECOND_POST;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.API_DOCUMENT_IDENTIFIER;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.operationRequestPreprocessor;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.operationResponsePreprocessor;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import univ.earthbreaker.namu.core.api.PresentationTest;
import univ.earthbreaker.namu.core.domain.post.PostRetrieveAllQuery;
import univ.earthbreaker.namu.core.domain.post.PostRetrieveService;
import univ.earthbreaker.namu.core.domain.post.RelatedPostRetrieveQuery;

class PostRetrieveControllerTest extends PresentationTest {

	private static final String RETRIEVE_ALL_URI = "/v1/posts/all";
	private static final String RETRIEVE_RELATED_URI = "/v1/posts/related/{missionNo}";

	private final PostRetrieveService postRetrieveService = Mockito.mock(PostRetrieveService.class);
	private final PostRetrieveController postRetrieveController = new PostRetrieveController(postRetrieveService);

	private static final String TEST_IMAGE_ACCESS_URL = "https://namu.test.image.com";

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = mockControllerWithAuthorization(postRetrieveController);
		System.setProperty(IMAGE_SYSTEM_PROPERTY, TEST_IMAGE_ACCESS_URL);
	}

	@DisplayName("회원 번호와 조회하고자 하는 날짜를 받아, 해당 날짜에 성공한 미션에 대한 게시글들을 반환하고, status 200 을 반환한다")
	@Test
	void retrieveAll() throws Exception {
		// given
		PostDateRequest requestBody = new PostDateRequest(LocalDate.parse("2024-04-17"));
		Mockito.when(postRetrieveService.retrieveAll(Mockito.any(PostRetrieveAllQuery.class)))
			.thenReturn(POSTS);

		// when
		ResultActions resultActions = whenGetWithAuthorization(RETRIEVE_ALL_URI, requestBody);

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].postNo").value(FIRST_POST.getNo()))
			.andExpect(jsonPath("$[0].title").value(FIRST_POST.getTitle()))
			.andExpect(jsonPath("$[0].imageUrl").value(System.getProperty(IMAGE_SYSTEM_PROPERTY) + FIRST_POST.getImagePath()))
			.andExpect(jsonPath("$[1].postNo").value(SECOND_POST.getNo()))
			.andExpect(jsonPath("$[1].title").value(SECOND_POST.getTitle()))
			.andExpect(jsonPath("$[1].imageUrl").value(System.getProperty(IMAGE_SYSTEM_PROPERTY) + SECOND_POST.getImagePath()));

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
					requestFields(
						fieldWithPath("postDate").type(STRING).description("조회하고자 하는 날짜")
					),
					responseFields(
						fieldWithPath("[].postNo").type(NUMBER).description("게시글 번호"),
						fieldWithPath("[].title").type(STRING).description("게시글 제목"),
						fieldWithPath("[].imageUrl").type(STRING).description("게시글 이미지")
					)
				)
			);
	}

	@DisplayName("회원 번호와 관련 미션 번호, 페이징 파라미터를 받아, 연관된 다른 게시글들을 조회하고 status 200 을 반환한다")
	@Test
	void retrieveRelated() throws Exception {
		// given
		Mockito.when(postRetrieveService.retrieveRelated(Mockito.any(RelatedPostRetrieveQuery.class)))
			.thenReturn(RELATED_POST_FIRST_PAGE_RESULT);
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("page", "0");
		queryParams.add("size", "5");
		queryParams.add("sort", null);

		// when
		ResultActions resultActions = whenGetWithAuthorization(RETRIEVE_RELATED_URI, FIRST_MISSION_NO, queryParams);

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.isLastPage").value(RELATED_POST_FIRST_PAGE_RESULT.isLast()))
			.andExpect(jsonPath("$.posts[0].postNo").value(FIRST_POST.getNo()))
			.andExpect(jsonPath("$.posts[0].memberNo").value(FIRST_POST.getMemberNo()))
			.andExpect(jsonPath("$.posts[0].nickname").value(FIRST_POST.getMemberNickname()))
			.andExpect(jsonPath("$.posts[0].title").value(FIRST_POST.getTitle()))
			.andExpect(jsonPath("$.posts[0].imageUrl").value(System.getProperty(IMAGE_SYSTEM_PROPERTY) + FIRST_POST.getImagePath()))
			.andExpect(jsonPath("$.posts[1].postNo").value(SECOND_POST.getNo()))
			.andExpect(jsonPath("$.posts[1].memberNo").value(SECOND_POST.getMemberNo()))
			.andExpect(jsonPath("$.posts[1].nickname").value(SECOND_POST.getMemberNickname()))
			.andExpect(jsonPath("$.posts[1].title").value(SECOND_POST.getTitle()))
			.andExpect(jsonPath("$.posts[1].imageUrl").value(System.getProperty(IMAGE_SYSTEM_PROPERTY) + SECOND_POST.getImagePath()));

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
					pathParameters(
						parameterWithName("missionNo").description("게시글과 연관된 미션 번호")
					),
					queryParameters(
						parameterWithName("page").description("페이지 번호 - (0부터 시작)"),
						parameterWithName("size").description("한 페이지당 표시할 게시글 수 - (최소 4, 최대 10)"),
						parameterWithName("sort").description("정렬 정보 - {정렬 필드}#{정렬 순서}")
					),
					responseFields(
						fieldWithPath("isLastPage").type(BOOLEAN).description("현재 페이지가 마지막 페이지인지 여부"),
						fieldWithPath("posts").type(ARRAY).description("연관된 게시글 목록"),
						fieldWithPath("posts[].postNo").type(NUMBER).description("게시글 번호"),
						fieldWithPath("posts[].memberNo").type(NUMBER).description("게시글을 작성한 회원의 번호"),
						fieldWithPath("posts[].nickname").type(STRING).description("게시글을 작성한 회원의 닉네임"),
						fieldWithPath("posts[].title").type(STRING).description("게시글의 제목"),
						fieldWithPath("posts[].imageUrl").type(STRING).description("게시글 이미지")
					)
				)
			);
	}
}
