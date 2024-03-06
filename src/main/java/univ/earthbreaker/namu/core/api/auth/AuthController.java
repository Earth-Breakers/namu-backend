package univ.earthbreaker.namu.core.api.auth;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.core.api.auth.support.HttpHeaderUtils;
import univ.earthbreaker.namu.core.domain.account.AccountService;
import univ.earthbreaker.namu.core.domain.account.LoginResult;
import univ.earthbreaker.namu.external.oauth.OAuthClientApi;
import univ.earthbreaker.namu.external.oauth.OAuthMemberInfoResult;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

	private final OAuthClientApi oAuthClientApi;
	private final AccountService accountService;

	public AuthController(OAuthClientApi oAuthClientApi, AccountService accountService) {
		this.oAuthClientApi = oAuthClientApi;
		this.accountService = accountService;
	}

	@PostMapping("/login/kakao")
	public ResponseEntity<Void> loginOrJoin(@RequestBody @NotNull LoginRequest request) {
		OAuthMemberInfoResult oAuthResult = oAuthClientApi.getOAuthMemberInfo(request.socialToken());
		LoginResult loginResult = accountService.loginOrJoin(request.toCommand(oAuthResult.id(), oAuthResult.nickname()));
		if (loginResult.isNewMember()) {
			return ResponseEntity.status(HttpStatus.CREATED)
				.header(HttpHeaders.AUTHORIZATION, HttpHeaderUtils.withBearerToken(loginResult.accessToken()))
				.header(HttpHeaderUtils.REFRESH_TOKEN, loginResult.refreshToken())
				.build();
		}
		return ResponseEntity.ok()
			.header(HttpHeaders.AUTHORIZATION, HttpHeaderUtils.withBearerToken(loginResult.accessToken()))
			.header(HttpHeaderUtils.REFRESH_TOKEN, loginResult.refreshToken())
			.build();
	}
}
