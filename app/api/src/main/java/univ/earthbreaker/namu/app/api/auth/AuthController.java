package univ.earthbreaker.namu.app.api.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.core.domain.account.AccountService;
import univ.earthbreaker.namu.core.domain.account.LoginResult;
import univ.earthbreaker.namu.app.support.HttpHeaderUtils;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

	private final AccountService accountService;

	public AuthController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping("/login/kakao")
	public ResponseEntity<Void> loginOrJoin(@RequestBody LoginRequest request) {
		LoginResult loginResult = accountService.loginOrJoin(request.toCommand());
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
