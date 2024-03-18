package univ.earthbreaker.namu.core.api;

import static org.mockito.ArgumentMatchers.any;

import org.jetbrains.annotations.NotNull;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import univ.earthbreaker.namu.core.api.advice.NamuExceptionResponseHandler;
import univ.earthbreaker.namu.core.api.auth.AuthApiFixture;
import univ.earthbreaker.namu.core.api.auth.support.HttpHeaderUtils;
import univ.earthbreaker.namu.support.apidocs.ApiDocsAbstract;

public abstract class PresentationTest extends ApiDocsAbstract {

	private static final String AUTHORIZATION_TYPE_WITH_ACCESS_TOKEN = "Bearer " + AuthApiFixture.ACCESS_TOKEN;
	protected static final long AUTHORIZED_MEMBER_NO = 1L;

	protected MockMvc mockMvc;

	protected MockMvc mockController(Object controller) {
		return MockMvcBuilders.standaloneSetup(controller)
			.setControllerAdvice(new NamuExceptionResponseHandler())
			.apply(mockMvcDocumentConfigurer())
			.alwaysDo(MockMvcResultHandlers.print())
			.build();
	}

	protected MockMvc mockControllerWithAuthorization(Object controller) throws Exception {
		return MockMvcBuilders.standaloneSetup(controller)
			.setControllerAdvice(new NamuExceptionResponseHandler())
			.setCustomArgumentResolvers(mockLoginMemberArgumentResolver())
			.apply(mockMvcDocumentConfigurer())
			.alwaysDo(MockMvcResultHandlers.print())
			.build();
	}

	private @NotNull HandlerMethodArgumentResolver mockLoginMemberArgumentResolver() throws Exception {
		HandlerMethodArgumentResolver loginMemberArgumentResolver = Mockito.mock(HandlerMethodArgumentResolver.class);
		Mockito.when(loginMemberArgumentResolver.supportsParameter(any(MethodParameter.class)))
			.thenReturn(true);
		Mockito.when(loginMemberArgumentResolver.resolveArgument(
				any(MethodParameter.class),
				any(ModelAndViewContainer.class),
				any(NativeWebRequest.class),
				any(WebDataBinderFactory.class)))
			.thenReturn(AUTHORIZED_MEMBER_NO);
		return loginMemberArgumentResolver;
	}

	protected ResultActions whenPost(String uri, String refreshToken) throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri)
			.contentType(MediaType.APPLICATION_JSON)
			.header(HttpHeaderUtils.REFRESH_TOKEN, refreshToken);
		return mockMvc.perform(requestBuilder);
	}

	protected ResultActions whenPost(String uri, Object requestBody) throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri)
			.contentType(MediaType.APPLICATION_JSON)
			.content(toJson(requestBody))
			.accept(MediaType.APPLICATION_JSON);
		return mockMvc.perform(requestBuilder);
	}

	protected ResultActions whenPostWithAuthorization(String uri) throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri)
			.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_TYPE_WITH_ACCESS_TOKEN)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON);
		return mockMvc.perform(requestBuilder);
	}

	protected ResultActions whenPostWithAuthorization(String uri, Long pathVariable) throws Exception {
		MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders.post(uri, pathVariable)
			.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_TYPE_WITH_ACCESS_TOKEN)
			.contentType(MediaType.APPLICATION_JSON);
		return mockMvc.perform(requestBuilder);
	}

	protected ResultActions whenPostWithAuthorization(String uri, Object requestBody) throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri)
			.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_TYPE_WITH_ACCESS_TOKEN)
			.contentType(MediaType.APPLICATION_JSON)
			.content(toJson(requestBody))
			.accept(MediaType.APPLICATION_JSON);
		return mockMvc.perform(requestBuilder);
	}

	protected ResultActions whenPostWithAuthorization(String uri, Long pathVariable, Object requestBody) throws Exception {
		MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders.post(uri, pathVariable)
			.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_TYPE_WITH_ACCESS_TOKEN)
			.contentType(MediaType.APPLICATION_JSON)
			.content(toJson(requestBody))
			.accept(MediaType.APPLICATION_JSON);
		return mockMvc.perform(requestBuilder);
	}

	protected ResultActions whenGetWithAuthorization(String uri) throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri)
			.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_TYPE_WITH_ACCESS_TOKEN)
			.accept(MediaType.APPLICATION_JSON);
		return mockMvc.perform(requestBuilder);
	}

	private String toJson(Object request) {
		try {
			return new ObjectMapper().writeValueAsString(request);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private  <T> T fromJson(String content, Class<T> clazz) {
		try {
			return new ObjectMapper().readValue(content, clazz);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
