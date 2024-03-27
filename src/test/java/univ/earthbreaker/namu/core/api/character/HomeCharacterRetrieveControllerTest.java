package univ.earthbreaker.namu.core.api.character;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static univ.earthbreaker.namu.core.api.Constant.IMAGE_ACCESS_URL;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.BEGIN_CURRENT_CHARACTER;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.BEGIN_REQUIRED_EXP;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.INITIAL_EXP;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.MEMBER_NO;
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

import univ.earthbreaker.namu.core.api.PresentationTest;
import univ.earthbreaker.namu.core.domain.character.HomeCharacterRetrieveService;

class HomeCharacterRetrieveControllerTest extends PresentationTest {

	private static final String HOME_CHARACTER_URI = "/v1/characters/home";
	private static final String TEST_IMAGE_ACCESS_URL = "https://namu.test.image.com";

	private final HomeCharacterRetrieveService homeCharacterRetrieveService
		= Mockito.mock(HomeCharacterRetrieveService.class);
	private final HomeCharacterRetrieveController homeCharacterRetrieveController
		= new HomeCharacterRetrieveController(homeCharacterRetrieveService);

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = mockControllerWithAuthorization(homeCharacterRetrieveController);
		System.setProperty(IMAGE_ACCESS_URL, TEST_IMAGE_ACCESS_URL);
	}

	@DisplayName("회원이 현재 보유하고 있는 에너지 포인트를 조회한다")
	@Test
	void retrieve() throws Exception {
		// given
		Mockito.when(homeCharacterRetrieveService.retrieveHomeCharacter(MEMBER_NO))
			.thenReturn(BEGIN_CURRENT_CHARACTER);

		// when
		ResultActions resultActions = whenGetWithAuthorization(HOME_CHARACTER_URI);

		// then
		String expectImagePath = System.getProperty(IMAGE_ACCESS_URL) + BEGIN_CURRENT_CHARACTER.getTargetCharacterMainImage();
		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.imageUrl").value(expectImagePath))
			.andExpect(jsonPath("$.requiredExp").value(BEGIN_REQUIRED_EXP))
			.andExpect(jsonPath("$.currentExp").value(INITIAL_EXP));

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
						fieldWithPath("imageUrl").type(STRING).description("현재 캐릭터 이미지 url"),
						fieldWithPath("requiredExp").type(NUMBER).description("현재 캐릭터의 요구 경험치"),
						fieldWithPath("currentExp").type(NUMBER).description("현재 캐릭터의 현재 경험치")
					)
				)
			);
	}
}
