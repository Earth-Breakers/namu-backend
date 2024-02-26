package univ.earthbreaker.namu.core.api.config;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import univ.earthbreaker.namu.core.api.auth.support.AuthenticationInterceptor;
import univ.earthbreaker.namu.core.api.auth.support.LoginMemberArgumentResolver;
import univ.earthbreaker.namu.core.api.auth.support.RefreshTokenArgumentResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final AuthenticationInterceptor authenticationInterceptor;
	private final LoginMemberArgumentResolver loginMemberArgumentResolver;
	private final RefreshTokenArgumentResolver refreshTokenArgumentResolver;

	public WebConfig(
		AuthenticationInterceptor authenticationInterceptor,
		LoginMemberArgumentResolver loginMemberArgumentResolver,
		RefreshTokenArgumentResolver refreshTokenArgumentResolver
	) {
		this.authenticationInterceptor = authenticationInterceptor;
		this.loginMemberArgumentResolver = loginMemberArgumentResolver;
		this.refreshTokenArgumentResolver = refreshTokenArgumentResolver;
	}

	@Override
	public void addInterceptors(@NotNull InterceptorRegistry registry) {
		registry.addInterceptor(authenticationInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns("/")
			.excludePathPatterns("/v1/auth/login/kakao")
			.excludePathPatterns("/v1/auth/reissue");
	}

	@Override
	public void addArgumentResolvers(@NotNull List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(loginMemberArgumentResolver);
		resolvers.add(refreshTokenArgumentResolver);
	}
}
