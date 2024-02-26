package univ.earthbreaker.namu.core.api.auth.support;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(@NotNull MethodParameter parameter) {
		return parameter.hasParameterAnnotation(LoginMember.class)
			&& parameter.getParameterType().equals(Long.class);
	}

	@Override
	public Object resolveArgument(
		@NotNull MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		@NotNull NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory
	) {
		checkHasAuthAnnotation(parameter);
		Long validMemberNo = (Long) webRequest.getAttribute(HttpHeaderUtils.ATTRIBUTE_NAME, RequestAttributes.SCOPE_REQUEST);
		checkGetLoginMemberNo(validMemberNo, parameter);
		return validMemberNo;
	}

	private void checkHasAuthAnnotation(@NotNull MethodParameter parameter) {
		if (parameter.getMethodAnnotation(AuthMapping.class) == null) {
			throw InternalAuthServerException.annotationNotFound();
		}
	}

	private void checkGetLoginMemberNo(Long validUserId, MethodParameter parameter) {
		if (validUserId == null) {
			throw InternalAuthServerException.attributeNotFound(
				HttpHeaderUtils.ATTRIBUTE_NAME, parameter.getClass(), parameter.getMethod());
		}
	}
}
