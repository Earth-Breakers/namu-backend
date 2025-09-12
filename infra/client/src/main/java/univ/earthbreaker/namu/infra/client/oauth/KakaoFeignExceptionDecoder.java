package univ.earthbreaker.namu.infra;

import org.jetbrains.annotations.NotNull;

import feign.Response;
import feign.codec.ErrorDecoder;
import univ.earthbreaker.namu.infra.client.oauth.OAuthClientException;

class KakaoFeignExceptionDecoder implements ErrorDecoder {

	private static final int KAKAO_UNAUTHORIZED = 401;

	@Override
	public Exception decode(String methodKey, @NotNull Response response) {
		if (response.status() == KAKAO_UNAUTHORIZED) {
			return OAuthClientException.unauthorized();
		}
		return OAuthClientException.another();
	}
}
