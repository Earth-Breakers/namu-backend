package univ.earthbreaker.namu.core.api.auth.support;

import java.lang.reflect.Method;

import org.jetbrains.annotations.NotNull;

public class InternalAuthServerException extends RuntimeException {

	private InternalAuthServerException(String message) {
		super(message);
	}

	static @NotNull InternalAuthServerException annotationNotFound() {
		return new InternalAuthServerException("@AuthMapping 어노테이션이 필요한 컨트롤러 입니다");
	}

	static @NotNull InternalAuthServerException attributeNotFound(String attribute, Class<?> clazz, Method method) {
		return new InternalAuthServerException(String.format("(%s) 를 가져오지 못했습니다 (%s - %s)", attribute, clazz, method));
	}
}
