package univ.earthbreaker.namu.core.api;

import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.API_DOCUMENT_IDENTIFIER;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.operationRequestPreprocessor;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.operationResponsePreprocessor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class CommonExceptionControllerTest extends PresentationTest {

	@DisplayName("")
	@Test
	void exception() throws Exception {
		// given
		mockMvc = mockController(new CommonExceptionController());

		// apidocs
		whenPost("/test/exception", new CommonExceptionController.ExceptionRequest("body"))
			.andExpect(MockMvcResultMatchers.status().isInternalServerError())
			.andDo(
				MockMvcRestDocumentation.document(
					API_DOCUMENT_IDENTIFIER,
					operationRequestPreprocessor(),
					operationResponsePreprocessor(),
					PayloadDocumentation.responseFields(
						PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("예외 메시지")
					)
				)
			);
	}
}
