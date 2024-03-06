package univ.earthbreaker.namu.support.apidocs;

import org.jetbrains.annotations.NotNull;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;

public class ApiDocsUtils {

	public static final String API_DOCUMENT_IDENTIFIER = "{class-name}/{method-name}";

	public static @NotNull OperationRequestPreprocessor operationRequestPreprocessor() {
		return Preprocessors.preprocessRequest(
			Preprocessors.modifyUris().scheme("http").host("namu.api.docs").removePort(),
			Preprocessors.prettyPrint()
		);
	}

	public static @NotNull OperationResponsePreprocessor operationResponsePreprocessor() {
		return Preprocessors.preprocessResponse(Preprocessors.prettyPrint());
	}
}
