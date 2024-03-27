package univ.earthbreaker.namu.core.api.character;

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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.ResultActions;

import univ.earthbreaker.namu.core.api.PresentationTest;
import univ.earthbreaker.namu.core.domain.character.CurrentCharacterGrowService;
import univ.earthbreaker.namu.core.domain.character.CurrentCharacterNotFoundException;
import univ.earthbreaker.namu.core.domain.character.NamuCharacterNotFoundException;

class CurrentCharacterGrowControllerTest extends PresentationTest {

	private static final String GROW_TO_NEXT_URI = "/v1/characters/grow/next";
	private static final String GROW_TO_RANDOM_URI = "/v1/characters/grow/random";

	private final CurrentCharacterGrowService currentCharacterGrowService
		= Mockito.mock(CurrentCharacterGrowService.class);
	private final CurrentCharacterGrowController currentCharacterGrowController
		= new CurrentCharacterGrowController(currentCharacterGrowService);

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = mockControllerWithAuthorization(currentCharacterGrowController);
	}

	@DisplayName("레벨이 MIDDLE 인 캐릭터를 다음 캐릭터로 성장시키고, 204 를 반환한다")
	@Test
	void growToNextLevelCharacter() throws Exception {
		// when
		ResultActions resultActions = whenPostWithAuthorization(GROW_TO_NEXT_URI);

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

	@DisplayName("레벨이 BEGIN 인 캐릭터를 다음 랜덤 캐릭터로 성장시키고, 204 를 반환한다")
	@Test
	void growToNextLevelRandomCharacter() throws Exception {
		// when
		ResultActions resultActions = whenPostWithAuthorization(GROW_TO_RANDOM_URI);

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

	@DisplayName("현재 캐릭터가 성장할 수 없는 상태이거나, 성장 가능한 레벨 값을 초과하면 예외를 발생시키고 400 을 반환한다")
	@ParameterizedTest
	@ValueSource(strings = {
		"현재 캐릭터는 아직 성장할 수 없는 상태입니다",
		"잘못된 요청으로 현재 혀용하는 level 의 최대치를 초과했습니다"
	})
	void fail_growToMethod_invalid(String exceptionMessage) throws Exception {
		// given
		Mockito.doThrow(new IllegalStateException(exceptionMessage))
			.when(currentCharacterGrowService)
			.growToNextLevel(AUTHORIZED_MEMBER_NO);

		// when
		ResultActions resultActions = whenPostWithAuthorization(GROW_TO_NEXT_URI);

		// then, apidocs
		resultActions
			.andExpect(status().isBadRequest())
			.andExpect(result ->
				assertThat(result.getResolvedException())
					.isInstanceOf(IllegalStateException.class)
					.hasMessage(exceptionMessage)
			)
			.andDo(commonExceptionDocumentResultHandler());
	}

	@DisplayName("회원의 현재 캐릭터가 존재하지 않으면 404 를 반환한다")
	@Test
	void fail_growToMethod_not_found() throws Exception {
		// given
		Mockito.doThrow(CurrentCharacterNotFoundException.notFound(AUTHORIZED_MEMBER_NO))
			.when(currentCharacterGrowService)
			.growToNextLevel(AUTHORIZED_MEMBER_NO);

		// when
		ResultActions resultActions = whenPostWithAuthorization(GROW_TO_NEXT_URI);

		// then, apidocs
		resultActions
			.andExpect(status().isNotFound())
			.andExpect(result ->
				assertThat(result.getResolvedException())
					.isInstanceOf(CurrentCharacterNotFoundException.class)
					.hasMessage(CurrentCharacterNotFoundException.notFound(AUTHORIZED_MEMBER_NO).getMessage())
			)
			.andDo(commonExceptionDocumentResultHandler());
	}

	@DisplayName("회원이 성장시킬 현재 캐릭터의 레벨이 MIDDLE 이 아니면 예외를 발생시키고 400 을 반환한다")
	@Test
	void fail_growToNextLevelCharacter_valid_level_is_middle() throws Exception {
		// given
		final String EXCEPTION_MESSAGE = "레벨이 MIDDLE 인 캐릭터만 growToNext 메서드를 호출할 수 있습니다";
		Mockito.doThrow(new IllegalStateException(EXCEPTION_MESSAGE))
			.when(currentCharacterGrowService)
			.growToNextLevel(AUTHORIZED_MEMBER_NO);

		// when
		ResultActions resultActions = whenPostWithAuthorization(GROW_TO_NEXT_URI);

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

	@DisplayName("회원이 성장시킬 캐릭터의 다음 레벨의 캐릭터가 존재하지 않으면 예외를 발생시키고 404 를 반환한다")
	@Test
	void fail_growToNextLevelCharacter_not_found() throws Exception {
		// given
		Mockito.doThrow(NamuCharacterNotFoundException.notFoundNext())
			.when(currentCharacterGrowService)
			.growToNextLevel(AUTHORIZED_MEMBER_NO);

		// when
		ResultActions resultActions = whenPostWithAuthorization(GROW_TO_NEXT_URI);

		// then, apidocs
		resultActions
			.andExpect(status().isNotFound())
			.andExpect(result ->
				assertThat(result.getResolvedException())
					.isInstanceOf(NamuCharacterNotFoundException.class)
					.hasMessage(NamuCharacterNotFoundException.notFoundNext().getMessage())
			)
			.andDo(commonExceptionDocumentResultHandler());
	}

	@DisplayName("회원이 성장시킬 현재 캐릭터의 레벨이 BEGIN 이 아니면 예외를 발생시키고 400 을 반환한다")
	@Test
	void fail_growToNextLevelRandomCharacter_valid_level_is_begin() throws Exception {
		// given
		final String EXCEPTION_MESSAGE = "레벨이 BEGIN 인 캐릭터만 growToRandom 메서드를 호출할 수 있습니다";
		Mockito.doThrow(new IllegalStateException(EXCEPTION_MESSAGE))
			.when(currentCharacterGrowService)
			.growToNextRandom(AUTHORIZED_MEMBER_NO);

		// when
		ResultActions resultActions = whenPostWithAuthorization(GROW_TO_RANDOM_URI);

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

	@DisplayName("회원이 성장시킬 캐릭터의 다음 랜덤 캐릭터가 존재하지 않으면 예외를 발생시키고 404 를 반환한다")
	@Test
	void fail_growToNextLevelRandomCharacter_not_found() throws Exception {
		// given
		Mockito.doThrow(NamuCharacterNotFoundException.notFoundRandom())
			.when(currentCharacterGrowService)
			.growToNextRandom(AUTHORIZED_MEMBER_NO);

		// when
		ResultActions resultActions = whenPostWithAuthorization(GROW_TO_RANDOM_URI);

		// then, apidocs
		resultActions
			.andExpect(status().isNotFound())
			.andExpect(result ->
				assertThat(result.getResolvedException())
					.isInstanceOf(NamuCharacterNotFoundException.class)
					.hasMessage(NamuCharacterNotFoundException.notFoundRandom().getMessage())
			)
			.andDo(commonExceptionDocumentResultHandler());
	}
}
