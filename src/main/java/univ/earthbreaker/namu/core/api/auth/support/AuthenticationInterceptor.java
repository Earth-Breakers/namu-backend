package univ.earthbreaker.namu.core.api.auth.support;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import univ.earthbreaker.namu.core.auth.JwtManager;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

	private final JwtManager jwtManager;

	public AuthenticationInterceptor(JwtManager jwtManager) {
		this.jwtManager = jwtManager;
	}

	@Override
	public boolean preHandle(
		@NotNull HttpServletRequest request,
		@NotNull HttpServletResponse response,
		@NotNull Object handler
	) {
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		AuthMapping authMapping = handlerMethod.getMethodAnnotation(AuthMapping.class);
		checkHasAnnotation(authMapping);

		String bearerTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		checkHeaderHasToken(bearerTokenHeader);
		checkIsBearerType(bearerTokenHeader);

		String accessToken = bearerTokenHeader.substring(HttpHeaderUtils.AUTHENTICATION_TYPE.length());
		jwtManager.validate(accessToken);

		Object payload = jwtManager.getPayload(accessToken);
		request.setAttribute(HttpHeaderUtils.ATTRIBUTE_NAME, payload);
		return true;
	}

	private void checkHasAnnotation(AuthMapping authMapping) {
		if (authMapping == null) {
			throw InternalAuthServerException.annotationNotFound();
		}
	}

	void checkHeaderHasToken(String token) {
		if (!StringUtils.hasText(token)) {
			throw AuthenticationException.tokenNotFound(token);
		}
	}

	private void checkIsBearerType(@NotNull String bearerTokenHeader) {
		if (!bearerTokenHeader.startsWith(HttpHeaderUtils.AUTHENTICATION_TYPE)) {
			throw AuthenticationException.authenticationTypeNotFound();
		}
	}
}
