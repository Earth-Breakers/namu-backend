package univ.earthbreaker.namu.clients.point;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import univ.earthbreaker.namu.server.external.Headers;
import univ.earthbreaker.namu.server.external.api.point.PointIssueRequest;

@FeignClient(
	name = "pointApiCaller",
	url = "http://localhost:8082/external/point",
	configuration = PointFeignConfiguration.class)
public interface PointApiCaller {

	@PostMapping(value = "/issue")
	ExternalPointResult issuePoint(
		@RequestHeader(value = Headers.IDEMPOTENCY_KEY, required = false) String idempotencyKey,
		@RequestBody PointIssueRequest request
	);
}
