package univ.earthbreaker.namu.support.apidocs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.API_DOCUMENT_IDENTIFIER;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.operationRequestPreprocessor;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.operationResponsePreprocessor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;

@ExtendWith(RestDocumentationExtension.class)
public abstract class ApiDocsAbstract {

	protected RestDocumentationContextProvider restDocumentation;

	@BeforeEach
	void setUp(RestDocumentationContextProvider restDocumentation) {
		this.restDocumentation = restDocumentation;
	}

	protected MockMvcConfigurer mockMvcDocumentConfigurer() {
		return MockMvcRestDocumentation.documentationConfiguration(restDocumentation);
	}

	protected ResultHandler commonExceptionDocumentResultHandler() {
		return document(
			API_DOCUMENT_IDENTIFIER,
			operationRequestPreprocessor(),
			operationResponsePreprocessor(),
			responseFields(
				fieldWithPath("message").type(JsonFieldType.STRING).description("예외 메시지")
			)
		);
	}
}
