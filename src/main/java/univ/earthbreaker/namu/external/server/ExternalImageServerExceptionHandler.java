package univ.earthbreaker.namu.external.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExternalImageServerExceptionHandler {

	@ExceptionHandler(ExternalBadRequestException.class)
	public ResponseEntity<ExternalImageResponse> handle400Exception(ExternalBadRequestException exception) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(new ExternalImageResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
	}

	@ExceptionHandler(ExternalImageServerException.class)
	public ResponseEntity<ExternalImageResponse> handle500Exception(ExternalImageServerException exception) {
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(new ExternalImageResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()));
	}
}

