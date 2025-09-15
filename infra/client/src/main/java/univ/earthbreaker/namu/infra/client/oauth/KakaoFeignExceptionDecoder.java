package univ.earthbreaker.namu.infra.client.oauth;

import feign.Response;
import feign.codec.ErrorDecoder;

class KakaoFeignExceptionDecoder implements ErrorDecoder {

	private static final int KAKAO_UNAUTHORIZED = 401;

	@Override
	public Exception decode(String methodKey, Response response) {
		if (response.status() == KAKAO_UNAUTHORIZED) {
			return OAuthClientException.unauthorized();
		}
		return OAuthClientException.another();
	}
}
