package univ.earthbreaker.namu.core.api.auth.support;

import org.jetbrains.annotations.NotNull;

public class AuthenticationException extends RuntimeException {

	private AuthenticationException(String message) {
		super(message);
	}

	static @NotNull AuthenticationException tokenNotFound(String token) {
		return new AuthenticationException(String.format("헤더의 토큰 값 (%s) 를 찾을 수 없습니다", token));
	}

	static @NotNull AuthenticationException authenticationTypeNotFound() {
		return new AuthenticationException("Authorization 인증 타입 Bearer 가 누락되었습니다");
	}
}
