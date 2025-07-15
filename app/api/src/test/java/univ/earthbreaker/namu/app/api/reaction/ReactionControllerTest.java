package univ.earthbreaker.namu.app.api.reaction;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static univ.earthbreaker.namu.core.domain.reaction.ReactionFixture.REACTION_TYPE;
import static univ.earthbreaker.namu.core.domain.reaction.ReactionFixture.TARGET_NO;
import static univ.earthbreaker.namu.core.domain.reaction.ReactionFixture.TARGET_TYPE_POST;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.API_DOCUMENT_IDENTIFIER;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.operationRequestPreprocessor;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.operationResponsePreprocessor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.ResultActions;

import univ.earthbreaker.namu.app.api.PresentationTest;
import univ.earthbreaker.namu.core.domain.reaction.ReactionService;

class ReactionControllerTest extends PresentationTest {

	private static final String DO_REACTION_URI = "/v1/reactions/do-reaction";
	private static final String UNDO_REACTION_URI = "/v1/reactions/undo-reaction";

	private final ReactionService reactionService = Mockito.mock(ReactionService.class);
	private final ReactionController reactionController = new ReactionController(reactionService);

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = mockControllerWithAuthorization(reactionController);
	}

	@DisplayName("리액션을 남길 대상의 번호와 종류, 리액션 종류를 받아 리액션을 등록하고 status 204 를 반환한다")
	@Test
	void doReaction() throws Exception {
		// given
		ReactionRequest requestBody = new ReactionRequest(TARGET_NO, TARGET_TYPE_POST, REACTION_TYPE);

		// when
		ResultActions resultActions = whenPostWithAuthorization(DO_REACTION_URI, requestBody);

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
					),
					requestFields(
						fieldWithPath("targetNo").type(NUMBER).description("리액션을 남기고자 하는 대상 번호"),
						fieldWithPath("targetType").type(STRING).description("리액션을 남기고자 하는 대상의 종"),
						fieldWithPath("reactionType").type(STRING).description("리액션하고자 하는 종류")
					)
				)
			);
	}

	@DisplayName("리액션을 취소할 대상의 번호와 종류, 리액션 종류를 받아 리액션을 취소하고 status 204 를 반환한다")
	@Test
	void undoReaction() throws Exception {
		// given
		ReactionRequest requestBody = new ReactionRequest(TARGET_NO, TARGET_TYPE_POST, REACTION_TYPE);

		// when
		ResultActions resultActions = whenPostWithAuthorization(UNDO_REACTION_URI, requestBody);

		// then
		resultActions.andExpect(status().isNoContent());
	}
}
