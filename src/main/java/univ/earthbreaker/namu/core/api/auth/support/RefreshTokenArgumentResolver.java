package univ.earthbreaker.namu.core.api.auth.support;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class RefreshTokenArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(RefreshToken.class)
			&& parameter.getParameterType().equals(String.class);
	}

	@Override
	public Object resolveArgument(
		@NotNull MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		@NotNull NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory
	) {
		HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
		assert httpServletRequest != null;
		return httpServletRequest.getHeader(HttpHeaderUtils.REFRESH_TOKEN);
	}
}
