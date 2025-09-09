package univ.earthbreaker.namu.server.external.api.point;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import univ.earthbreaker.namu.server.external.exception.ExternalBadRequestException;
import univ.earthbreaker.namu.server.external.exception.ExternalImageServerException;

@RestControllerAdvice(basePackages = "univ.earthbreaker.namu.server.external")
public class ExternalPointServerExceptionHandler {

	@ExceptionHandler(ExternalBadRequestException.class)
	public ResponseEntity<ExternalPointResponse> handle400Exception(ExternalBadRequestException exception) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(new ExternalPointResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), null));
	}

	@ExceptionHandler(ExternalImageServerException.class)
	public ResponseEntity<ExternalPointResponse> handle500Exception(ExternalImageServerException exception) {
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(new ExternalPointResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), null));
	}
}
