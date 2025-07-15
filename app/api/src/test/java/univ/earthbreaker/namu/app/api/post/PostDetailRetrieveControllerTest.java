package univ.earthbreaker.namu.app.api.post;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static univ.earthbreaker.namu.core.domain.common.Constant.IMAGE_SYSTEM_PROPERTY;
import static univ.earthbreaker.namu.core.domain.post.PostFixture.FIRST_POST;
import static univ.earthbreaker.namu.core.domain.post.PostFixture.FIRST_POST_NO;
import static univ.earthbreaker.namu.core.domain.post.PostFixture.POST_REACTION_RESULT;
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

import univ.earthbreaker.namu.app.api.PresentationTest;
import univ.earthbreaker.namu.core.domain.post.PostDetailRetrieveService;
import univ.earthbreaker.namu.core.domain.post.PostRetrieveDetailQuery;

class PostDetailRetrieveControllerTest extends PresentationTest {

	private static final String RETRIEVE_DETAIL_URI = "/v1/posts/detail/{postNo}";

	private static final String TEST_IMAGE_ACCESS_URL = "https://namu.test.image.com";

	private final PostDetailRetrieveService postDetailRetrieveService = Mockito.mock(PostDetailRetrieveService.class);
	private final PostDetailRetrieveController postDetailRetrieveController
		= new PostDetailRetrieveController(postDetailRetrieveService);

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = mockControllerWithAuthorization(postDetailRetrieveController);
		System.setProperty(IMAGE_SYSTEM_PROPERTY, TEST_IMAGE_ACCESS_URL);
	}

	@DisplayName("회원 번호와 게시글 번호를 받아, 게시글에 대한 상세 정보를 조회하고 status 200 을 반환한다")
	@Test
	void retrieveDetail() throws Exception {
		// given
		Mockito.when(postDetailRetrieveService.retrieveDetail(Mockito.any(PostRetrieveDetailQuery.class)))
			.thenReturn(POST_REACTION_RESULT);

		// when
		ResultActions resultActions = whenGetWithAuthorization(RETRIEVE_DETAIL_URI, FIRST_POST_NO);

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.postNo").value(FIRST_POST.getNo()))
			.andExpect(jsonPath("$.memberNo").value(FIRST_POST.getMemberNo()))
			.andExpect(jsonPath("$.nickname").value(FIRST_POST.getMemberNickname()))
			.andExpect(jsonPath("$.title").value(FIRST_POST.getTitle()))
			.andExpect(jsonPath("$.content").value(FIRST_POST.getContent()))
			.andExpect(
				jsonPath("$.imageUrl").value(System.getProperty(IMAGE_SYSTEM_PROPERTY) + FIRST_POST.getImagePath()))
			.andExpect(jsonPath("$.relatedMissionNo").value(FIRST_POST.getMissionNo()))
			.andExpect(jsonPath("$.reactionStatus.infos[0].reactionType").value("LIKE"))
			.andExpect(jsonPath("$.reactionStatus.infos[0].reactionCount").value(1))
			.andExpect(jsonPath("$.reactionStatus.infos[0].reactionMembers[0]").value(2L))
			.andExpect(jsonPath("$.reactionStatus.alreadyReaction").value(false));

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
						parameterWithName("postNo").description("조회할 게시글 번호")
					),
					responseFields(
						fieldWithPath("postNo").type(NUMBER).description("게시글 번호"),
						fieldWithPath("memberNo").type(NUMBER).description("게시글을 작성한 회원의 번호"),
						fieldWithPath("nickname").type(STRING).description("게시글을 작성한 회원의 닉네임"),
						fieldWithPath("title").type(STRING).description("게시글 제목"),
						fieldWithPath("content").type(STRING).description("게시글 내용"),
						fieldWithPath("imageUrl").type(STRING).description("게시글 이미지"),
						fieldWithPath("relatedMissionNo").type(NUMBER).description("해당 게시물과 연관된 미션 번호"),
						fieldWithPath("reactionStatus").type(OBJECT).description("게시물에 대한 반응 상태 정보"),
						fieldWithPath("reactionStatus.infos").type(ARRAY).description("게시물 반응 정보 배열"),
						fieldWithPath("reactionStatus.infos[].reactionType").type(STRING).description("리액션 종류"),
						fieldWithPath("reactionStatus.infos[].reactionCount").type(NUMBER).description("해당 리액션의 갯수"),
						fieldWithPath("reactionStatus.infos[].reactionMembers").type(ARRAY).description("리액션을 한 회원들의 번호"),
						fieldWithPath("reactionStatus.alreadyReaction").type(BOOLEAN).description("현재 사용자가 이 게시물에 리액션했는지 여부")
					)
				)
			);
	}
}
