package univ.earthbreaker.namu.core.auth;

import org.jetbrains.annotations.NotNull;

public class UnAuthorizedException extends RuntimeException {

	private UnAuthorizedException(String message) {
		super(message);
	}

	static @NotNull UnAuthorizedException wrong(String token) {
		return new UnAuthorizedException(String.format("잘못된 토큰 (%s) 입니다", token));
	}

	static @NotNull UnAuthorizedException expired(String token) {
		return new UnAuthorizedException(String.format("만료된 토큰 (%s) 입니다", token));
	}

	static @NotNull UnAuthorizedException notFound(String refreshToken) {
		return new UnAuthorizedException(String.format("존재하지 않고 유효하지 않은 토큰 (%s) 입니다", refreshToken));
	}
}
