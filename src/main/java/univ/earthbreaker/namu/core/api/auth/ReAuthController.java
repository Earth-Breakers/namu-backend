package univ.earthbreaker.namu.core.api.auth;

import static univ.earthbreaker.namu.core.api.auth.support.HttpHeaderUtils.REFRESH_TOKEN;
import static univ.earthbreaker.namu.core.api.auth.support.HttpHeaderUtils.withBearerToken;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.core.auth.TokenReissueService;
import univ.earthbreaker.namu.core.auth.TokenResult;

@RestController
@RequestMapping("/v1/auth")
public class ReAuthController {

	private final TokenReissueService tokenReissueService;

	public ReAuthController(TokenReissueService tokenReissueService) {
		this.tokenReissueService = tokenReissueService;
	}

	@PostMapping("/reissue")
	public ResponseEntity<Void> reissue(@RequestHeader(value = REFRESH_TOKEN) String refreshTokenValue) {
		TokenResult tokenResult = tokenReissueService.reissue(refreshTokenValue);
		return ResponseEntity.ok()
			.header(HttpHeaders.AUTHORIZATION, withBearerToken(tokenResult.accessToken()))
			.build();
	}
}
