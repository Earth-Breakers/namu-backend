package univ.earthbreaker.namu.core.admin.api.advice;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdminExceptionResponseHandler {

	private static final Logger LOG = LoggerFactory.getLogger(AdminExceptionResponseHandler.class);

	@ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
	public ResponseEntity<AdminExceptionResponse> badRequest(@NotNull RuntimeException exception) {
		return ResponseEntity.badRequest()
			.body(new AdminExceptionResponse(
				HttpStatus.BAD_REQUEST.value(),
				exception.getMessage())
			);
	}
}
