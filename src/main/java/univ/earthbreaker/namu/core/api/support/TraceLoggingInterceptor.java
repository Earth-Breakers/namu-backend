package univ.earthbreaker.namu.core.api.support;

import java.util.StringJoiner;

import org.apache.logging.log4j.ThreadContext;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import univ.earthbreaker.namu.core.api.auth.support.HttpHeaderUtils;

@Component
public class TraceLoggingInterceptor implements HandlerInterceptor {

	private static final String TRACE_MDC_KEY = "traceId";
	private static final String DELIMITER = ".";

	@Override
	public boolean preHandle(
		@NotNull HttpServletRequest request,
		@NotNull HttpServletResponse response,
		@NotNull Object handler
	) {
		if (handler instanceof HandlerMethod handlerMethod) {
			String handlerName = handlerMethod.getBeanType().getSimpleName();
			String methodName = handlerMethod.getMethod().getName();
			Long validMemberNo = (Long) request.getAttribute(HttpHeaderUtils.ATTRIBUTE_NAME);
			String traceId = new StringJoiner(DELIMITER)
				.add(String.valueOf(validMemberNo))
				.add(handlerName)
				.add(methodName)
				.toString();
			ThreadContext.put(TRACE_MDC_KEY, traceId);
		}
		return true;
	}

	@Override
	public void afterCompletion(
		@NotNull HttpServletRequest request,
		@NotNull HttpServletResponse response,
		@NotNull Object handler,
		Exception ex
	) {
		ThreadContext.clearAll();
	}
}
