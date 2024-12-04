package univ.earthbreaker.namu.external.image;

import org.springframework.http.HttpStatus;

import feign.Response;
import feign.codec.ErrorDecoder;

public class ImageFeignExceptionDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		HttpStatus status = HttpStatus.resolve(response.status());
		if (status == HttpStatus.BAD_REQUEST) {
			throw new ImageServerBadRequestException(
				String.format("[이미지 서버 API BAD_REQUEST] %s : %s", methodKey, response.reason()));
		}
		throw new ImageServerServerException(
			String.format("[이미지 서버 API SERVER_ERROR] %s : %s", methodKey, response.reason()));
	}
}
