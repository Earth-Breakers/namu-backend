package univ.earthbreaker.namu.core.api.character;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static univ.earthbreaker.namu.core.api.Constant.IMAGE_ACCESS_URL;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.ACQUIRED_COUNT;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.BOOK_RESULT;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.CHARACTER_IMAGE_PATH;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.CHARACTER_NO;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.MEMBER_CHARACTER;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.MEMBER_NO;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.TOTAL_ACQUIRED_COUNT;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.TOTAL_COUNT_OF_TYPE;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import univ.earthbreaker.namu.core.api.PresentationTest;
import univ.earthbreaker.namu.core.domain.character.book.CharacterBookDetailReadService;
import univ.earthbreaker.namu.core.domain.character.book.CharacterBookReadService;

class CharacterBookReadControllerTest extends PresentationTest {

	private static final String ALL_CHARACTER_BOOK_URI = "/v1/characters/books/all";
	private static final String DETAIL_CHARACTER_BOOK_URI = "/v1/characters/books/detail/{characterNo}";

	private static final String TEST_IMAGE_ACCESS_URL = "https://namu.test.image.com";

	private final CharacterBookReadService characterBookReadService
		= Mockito.mock(CharacterBookReadService.class);
	private final CharacterBookDetailReadService characterBookDetailReadService
		= Mockito.mock(CharacterBookDetailReadService.class);
	private final CharacterBookReadController characterBookReadController
		= new CharacterBookReadController(characterBookReadService, characterBookDetailReadService);

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = mockControllerWithAuthorization(characterBookReadController);
		System.setProperty(IMAGE_ACCESS_URL, TEST_IMAGE_ACCESS_URL);
	}

	@DisplayName("회원의 캐릭터 도감 전체를 조회한다")
	@Test
	void readAll() throws Exception {
	    // given
		Mockito.when(characterBookReadService.readAll(MEMBER_NO))
			.thenReturn(BOOK_RESULT);

	    // when
		ResultActions resultActions = whenGetWithAuthorization(ALL_CHARACTER_BOOK_URI);

		// then
		resultActions
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.totalAcquiredCount").value(TOTAL_ACQUIRED_COUNT))
			.andExpect(jsonPath("$.sectionResults[0].totalCountOfType").value(TOTAL_COUNT_OF_TYPE))
			.andExpect(jsonPath("$.sectionResults[0].acquiredCount").value(ACQUIRED_COUNT))
			.andExpect(jsonPath("$.sectionResults[0].profileResults[0].characterNo").value(CHARACTER_NO))
			.andExpect(jsonPath("$.sectionResults[0].profileResults[0].thumbnailImagePath").value(CHARACTER_IMAGE_PATH))
			.andExpect(jsonPath("$.sectionResults[0].profileResults[0].isAcquired").value(true))
			.andExpect(jsonPath("$.sectionResults[0].profileResults[1].characterNo").value(0))
			.andExpect(jsonPath("$.sectionResults[0].profileResults[1].thumbnailImagePath").value(CHARACTER_IMAGE_PATH))
			.andExpect(jsonPath("$.sectionResults[0].profileResults[1].isAcquired").value(false));

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
						fieldWithPath("totalAcquiredCount").type(NUMBER).description("총 획득한 캐릭터 수"),
						fieldWithPath("sectionResults").type(ARRAY).description("섹션별 결과 목록"),
						fieldWithPath("sectionResults[].type").type(STRING).description("캐릭터 타입"),
						fieldWithPath("sectionResults[].totalCountOfType").type(NUMBER).description("해당 타입의 총 캐릭터 수"),
						fieldWithPath("sectionResults[].acquiredCount").type(NUMBER).description("획득한 해당 타입 캐릭터 수"),
						fieldWithPath("sectionResults[].profileResults").type(ARRAY).description("캐릭터 프로필 결과 목록"),
						fieldWithPath("sectionResults[].profileResults[].characterNo").type(NUMBER).description("캐릭터 번호 (획득하지 못한 캐릭터일 시 0)"),
						fieldWithPath("sectionResults[].profileResults[].thumbnailImagePath").type(STRING).description("캐릭터 썸네일 이미지 경로"),
						fieldWithPath("sectionResults[].profileResults[].isAcquired").type(BOOLEAN).description("캐릭터 획득 여부")
					)
				)
			);
	}

	@DisplayName("회원의 캐릭터 도감 중, 특정 캐릭터 번호를 받아 상세 정보를 조회한다")
	@Test
	void readDetail() throws Exception {
	    // given
		Mockito.when(characterBookDetailReadService.retrieveDetail(MEMBER_NO, CHARACTER_NO))
			.thenReturn(MEMBER_CHARACTER);
		CharacterDetailResponse expect = CharacterDetailResponse.from(MEMBER_CHARACTER);

		// when
		ResultActions resultActions = whenGetWithAuthorization(DETAIL_CHARACTER_BOOK_URI, CHARACTER_NO);

		// then
		resultActions
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.characterNo").value(expect.characterNo()))
			.andExpect(jsonPath("$.count").value(expect.count()))
			.andExpect(jsonPath("$.isEndangered").value(expect.isEndangered()))
			.andExpect(jsonPath("$.type").value(expect.type().name()))
			.andExpect(jsonPath("$.gender").value(expect.gender().name()))
			.andExpect(jsonPath("$.name").value(expect.name()))
			.andExpect(jsonPath("$.description").value(expect.description()))
			.andExpect(jsonPath("$.imageUrl").value(expect.imageUrl()));

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
						parameterWithName("characterNo").description("조회할 캐릭터의 번호")
					),
					responseFields(
						fieldWithPath("characterNo").type(NUMBER).description("캐릭터의 번호"),
						fieldWithPath("count").type(NUMBER).description("획득한 해당 캐릭터 수"),
						fieldWithPath("isEndangered").type(BOOLEAN).description("멸종 위기종 여부"),
						fieldWithPath("type").type(STRING).description("캐릭터 타입"),
						fieldWithPath("gender").type(STRING).description("캐릭터 성별"),
						fieldWithPath("name").type(STRING).description("캐릭터 이름"),
						fieldWithPath("description").type(STRING).description("캐릭터 상세 설명"),
						fieldWithPath("imageUrl").type(STRING).description("캐릭터 이미지 경로")
					)
				)
			);
	}
}
