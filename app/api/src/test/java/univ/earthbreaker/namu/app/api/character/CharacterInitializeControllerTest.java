package univ.earthbreaker.namu.app.api.character;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
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

import univ.earthbreaker.namu.app.api.PresentationTest;
import univ.earthbreaker.namu.core.domain.character.current.CurrentCharacterInitializeService;

class CharacterInitializeControllerTest extends PresentationTest {

	private static final String CHARACTER_INITIALIZE_URI = "/v1/characters/initialize";

	private final CurrentCharacterInitializeService currentCharacterInitializeService
		= Mockito.mock(CurrentCharacterInitializeService.class);
	private final CharacterInitializeController characterInitializeController
		= new CharacterInitializeController(currentCharacterInitializeService);

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = mockControllerWithAuthorization(characterInitializeController);
	}

	@DisplayName("레벨이 FINAL 인 캐릭터를 초기화하고, 204 를 반환한다")
	@Test
	void initializeAfterFinalCharacter() throws Exception {
		// when
		ResultActions resultActions = whenPostWithAuthorization(CHARACTER_INITIALIZE_URI);

		// then
		resultActions.andExpect(status().isNoContent());

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

	@DisplayName("회원이 성장시킬 현재 캐릭터의 레벨이 FINAL 이 아니면 예외를 발생시키고 400 을 반환한다")
	@Test
	void fail_initializeAfterFinalCharacter_valid_level_is_final() throws Exception {
		// given
		final String EXCEPTION_MESSAGE = "레벨이 FINAL 인 캐릭터만 initialize 메서드를 호출할 수 있습니다";
		Mockito.doThrow(new IllegalStateException(EXCEPTION_MESSAGE))
			.when(currentCharacterInitializeService)
			.initialize(AUTHORIZED_MEMBER_NO);

		// when
		ResultActions resultActions = whenPostWithAuthorization(CHARACTER_INITIALIZE_URI);

		// then, apidocs
		resultActions
			.andExpect(status().isBadRequest())
			.andExpect(result ->
				assertThat(result.getResolvedException())
					.isInstanceOf(IllegalStateException.class)
					.hasMessage(EXCEPTION_MESSAGE)
			)
			.andDo(commonExceptionDocumentResultHandler());
	}
}
