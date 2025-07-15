package univ.earthbreaker.namu.app.api.point;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static univ.earthbreaker.namu.core.domain.point.EnergyPointFixture.ENERGY_TYPE;
import static univ.earthbreaker.namu.core.domain.point.EnergyPointFixture.USE_POINT_VALUE;
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
import univ.earthbreaker.namu.core.domain.character.current.CurrentCharacterBadRequestException;
import univ.earthbreaker.namu.core.domain.point.EnergyPointProvideService;
import univ.earthbreaker.namu.core.domain.point.ProvideEnergyPointCommand;

class EnergyPointProvideControllerTest extends PresentationTest {

	private static final String USE_POINT_URI = "/v1/points/use";

	private final EnergyPointProvideService energyPointProvideService = Mockito.mock(EnergyPointProvideService.class);
	private final EnergyPointProvideController energyPointProvideController
		= new EnergyPointProvideController(energyPointProvideService);

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = mockControllerWithAuthorization(energyPointProvideController);
	}

	@DisplayName("현재 회원의 포인트를 사용해 회원의 현재 캐릭터에 에너지를 제공하고, status 204 를 반환한다")
	@Test
	void success_provideEnergyToCharacter() throws Exception {
		// given
		ProvideEnergyRequest request = new ProvideEnergyRequest(ENERGY_TYPE, USE_POINT_VALUE);

		// when
		ResultActions resultActions = whenPostWithAuthorization(USE_POINT_URI, request);

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
					)
				)
			);
	}

	@DisplayName("사용할 수 있는 포인트가 없으면 예외를 발생시키고 status 400 을 반환한다")
	@Test
	void fail_provideEnergyToCharacter_no_point() throws Exception {
		// given
		String exceptionMessage = "사용할 수 있는 포인트가 없습니다";
		ProvideEnergyRequest request = new ProvideEnergyRequest(ENERGY_TYPE, USE_POINT_VALUE);
		Mockito.doThrow(new IllegalArgumentException(exceptionMessage))
			.when(energyPointProvideService)
			.provideEnergyToCharacter(Mockito.any(ProvideEnergyPointCommand.class));

		// when
		ResultActions resultActions = whenPostWithAuthorization(USE_POINT_URI, request);

		// then, apidocs
		resultActions
			.andExpect(status().isBadRequest())
			.andExpect(result ->
				assertThat(result.getResolvedException())
					.isInstanceOf(IllegalArgumentException.class)
					.hasMessage(exceptionMessage)
			)
			.andDo(commonExceptionDocumentResultHandler());
	}

	@DisplayName("사용할 수 있는 최대 허용 포인트를 초과하면 예외를 발생시키고 status 400 을 반환한다")
	@Test
	void fail_provideEnergyToCharacter_overflow_point() throws Exception {
		// given
		String exceptionMessage = "사용할 수 있는 최대 허용 포인트를 초과했습니다";
		ProvideEnergyRequest request = new ProvideEnergyRequest(ENERGY_TYPE, USE_POINT_VALUE);
		Mockito.doThrow(new IllegalArgumentException(exceptionMessage))
			.when(energyPointProvideService)
			.provideEnergyToCharacter(Mockito.any(ProvideEnergyPointCommand.class));

		// when
		ResultActions resultActions = whenPostWithAuthorization(USE_POINT_URI, request);

		// then, apidocs
		resultActions
			.andExpect(status().isBadRequest())
			.andExpect(result ->
				assertThat(result.getResolvedException())
					.isInstanceOf(IllegalArgumentException.class)
					.hasMessage(exceptionMessage)
			)
			.andDo(commonExceptionDocumentResultHandler());
	}

	@DisplayName("제공한 에너지 타입의 종류와 현재 캐릭터의 타입이 맞지 않으면, 예외를 발생시키고 status 400 을 반환한다")
	@Test
	void fail_provideEnergyToCharacter_missMatch() throws Exception {
		// given
		ProvideEnergyRequest request = new ProvideEnergyRequest(ENERGY_TYPE, USE_POINT_VALUE);
		Mockito.doThrow(CurrentCharacterBadRequestException.missMatch())
			.when(energyPointProvideService)
			.provideEnergyToCharacter(Mockito.any(ProvideEnergyPointCommand.class));

		// when
		ResultActions resultActions = whenPostWithAuthorization(USE_POINT_URI, request);

		// then, apidocs
		resultActions
			.andExpect(status().isBadRequest())
			.andExpect(result ->
				assertThat(result.getResolvedException())
					.isInstanceOf(CurrentCharacterBadRequestException.class)
					.hasMessage(CurrentCharacterBadRequestException.missMatch().getMessage())
			)
			.andDo(commonExceptionDocumentResultHandler());
	}

	@DisplayName("제공한 에너지가 현재 캐릭터의 필요 에너지 이상이면, 예외를 발생시키고 status 400 을 반환한다")
	@Test
	void fail_provideEnergyToCharacter_expOverflow() throws Exception {
		// given
		ProvideEnergyRequest request = new ProvideEnergyRequest(ENERGY_TYPE, USE_POINT_VALUE);
		Mockito.doThrow(CurrentCharacterBadRequestException.expOverflow())
			.when(energyPointProvideService)
			.provideEnergyToCharacter(Mockito.any(ProvideEnergyPointCommand.class));

		// when
		ResultActions resultActions = whenPostWithAuthorization(USE_POINT_URI, request);

		// then, apidocs
		resultActions
			.andExpect(status().isBadRequest())
			.andExpect(result ->
				assertThat(result.getResolvedException())
					.isInstanceOf(CurrentCharacterBadRequestException.class)
					.hasMessage(CurrentCharacterBadRequestException.expOverflow().getMessage())
			)
			.andDo(commonExceptionDocumentResultHandler());
	}
}
