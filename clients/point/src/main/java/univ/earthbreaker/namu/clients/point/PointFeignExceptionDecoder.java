package univ.earthbreaker.namu.clients.point;

import org.springframework.http.HttpStatus;

import feign.Response;
import feign.codec.ErrorDecoder;

class PointFeignExceptionDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		HttpStatus status = HttpStatus.resolve(response.status());
		if (status == HttpStatus.BAD_REQUEST) {
			throw new PointServerServerException(
				String.format("[포인트 서버 API BAD_REQUEST] %s : %s", methodKey, response.reason()));
		}
		throw new PointServerServerException(
			String.format("[포인트 서버 API SERVER_ERROR] %s : %s", methodKey, response.reason()));
	}
}
