package univ.earthbreaker.namu.external.oauth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
	name = "${feign.client.oauth.kakao.name}",
	url = "${feign.client.oauth.kakao.url}",
	configuration = KakaoFeignConfiguration.class)
public interface KakaoApiCaller {

	String FORM_URLENCODED_UTF8 = MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=utf-8";

	@GetMapping(value = "/v2/user/me", produces = FORM_URLENCODED_UTF8)
	KakaoMemberInfoResponse getKakaoUserInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerAccessToken);
}
