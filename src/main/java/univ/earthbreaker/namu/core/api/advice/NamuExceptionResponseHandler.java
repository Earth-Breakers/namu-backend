package univ.earthbreaker.namu.core.api.advice;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import univ.earthbreaker.namu.core.api.auth.support.AuthenticationException;
import univ.earthbreaker.namu.core.api.auth.support.InternalAuthServerException;
import univ.earthbreaker.namu.core.domain.common.BadRequestException;
import univ.earthbreaker.namu.core.domain.common.ForbiddenException;
import univ.earthbreaker.namu.core.domain.common.InternalServerException;
import univ.earthbreaker.namu.core.domain.common.NotFoundException;
import univ.earthbreaker.namu.core.auth.UnAuthorizedException;
import univ.earthbreaker.namu.external.oauth.OAuthClientException;

@RestControllerAdvice
public class NamuExceptionResponseHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(NamuExceptionResponseHandler.class);

	@ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
	public ResponseEntity<NamuExceptionResponse> badRequest(@NotNull RuntimeException exception) {
		return ResponseEntity.badRequest()
			.body(new NamuExceptionResponse(exception.getMessage()));
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<NamuExceptionResponse> notFount(@NotNull NotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(new NamuExceptionResponse(exception.getMessage()));
	}

	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<NamuExceptionResponse> forbidden(@NotNull ForbiddenException exception) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
			.body(new NamuExceptionResponse(exception.getMessage()));
	}

	@ExceptionHandler({OAuthClientException.class, UnAuthorizedException.class, AuthenticationException.class})
	public ResponseEntity<NamuExceptionResponse> unauthorized(@NotNull RuntimeException exception) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			.body(new NamuExceptionResponse(exception.getMessage()));
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<NamuExceptionResponse> unauthorized(@NotNull BadRequestException exception) {
		return ResponseEntity.badRequest()
			.body(new NamuExceptionResponse(exception.getMessage()));
	}

	@ExceptionHandler({InternalServerException.class, InternalAuthServerException.class, RuntimeException.class})
	public ResponseEntity<NamuExceptionResponse> internalServerError(@NotNull RuntimeException exception) {
		LOGGER.warn("[500] INTERNAL SERVER ERROR : {}", exception.getMessage());
		return ResponseEntity.internalServerError()
			.body(new NamuExceptionResponse(exception.getMessage()));
	}
}
