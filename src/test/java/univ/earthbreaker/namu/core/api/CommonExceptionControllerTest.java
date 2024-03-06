package univ.earthbreaker.namu.core.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
			.andDo(commonExceptionDocumentResultHandler());
	}
}
