package univ.earthbreaker.namu.core.auth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.security.Key;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

class JwtManagerTest {

	private static final String JWT_SECRET = "namuTestJwtSecretNamuTestJwtSecretNamuTestJwtSecret";
	private static final Key SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
	private static final long ACCESS_TOKEN_VALID_DURATION = 1000 * 60 * 60;
	private static final long REFRESH_TOKEN_VALID_DURATION = 1000 * 60 * 60 * 7;
	private static final Long MEMBER_NO = 1L;
	private static final String CLAIM_NAME = "MEMBER_NO";

	private final JwtManager jwtManager = new JwtManager(
		JWT_SECRET,
		ACCESS_TOKEN_VALID_DURATION,
		REFRESH_TOKEN_VALID_DURATION
	);

	@DisplayName("payload 를 받아 accessToken 을 생성할 수 있다")
	@Test
	void createAccessToken() {
		// when
		String accessToken = jwtManager.createAccessToken(MEMBER_NO);

		// then
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(SECRET_KEY)
			.build()
			.parseClaimsJws(accessToken)
			.getBody();
		assertThat(claims.get(CLAIM_NAME, Long.class)).isEqualTo(MEMBER_NO);
	}

	@DisplayName("memberNo 를 받아 refreshToken 을 생성하면 정해진 만료시간을 갖는 refreshToken 을 반환한다")
	@Test
	void createRefreshToken() {
		// given
		Date refreshExpiresIn = new Date(new Date().getTime() + REFRESH_TOKEN_VALID_DURATION);

		// when
		RefreshToken refreshToken = jwtManager.createRefreshToken(MEMBER_NO);

		// then
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(SECRET_KEY)
			.build()
			.parseClaimsJws(refreshToken.getValue())
			.getBody();
		assertThat(claims.getExpiration()).isBeforeOrEqualTo(refreshExpiresIn);
	}

	@DisplayName("accessToken 에서 payload 를 가져올 수 있다")
	@Test
	void getPayload() {
		// given
		String accessToken = jwtManager.createAccessToken(MEMBER_NO);

		// when
		Long payload = (Long)jwtManager.getPayload(accessToken);

		// then
		assertThat(payload).isEqualTo(MEMBER_NO);
	}

	@DisplayName("정상적인 accessToken 에 대해 검증을 진행하면 아무 예외도 발생시키지 않는다")
	@Test
	void validate() {
		// given
		String accessToken = jwtManager.createAccessToken(MEMBER_NO);

		// when, then
		assertThatNoException()
			.isThrownBy(() -> jwtManager.validate(accessToken));
	}

	@DisplayName("잘못된 토큰을 검증하면 예외를 발생시킨다")
	@Test
	void fail_validate_UnAuthorizedException_wrong() {
		// when, then
		assertThatThrownBy(() -> jwtManager.validate("invalidToken"))
			.isInstanceOf(UnAuthorizedException.class)
			.hasMessage(UnAuthorizedException.wrong("invalidToken").getMessage());
	}

	@DisplayName("만료된 토큰을 검증하면 예외를 발생시킨다")
	@Test
	void fail_validate_UnAuthorizedException_expired() {
		// given
		String expiredToken = generateExpiredToken();

		// when, then
		assertThatThrownBy(() -> jwtManager.validate(expiredToken))
			.isInstanceOf(UnAuthorizedException.class)
			.hasMessage(UnAuthorizedException.expired(expiredToken).getMessage());
	}

	private String generateExpiredToken() {
		Date now = new Date();
		Date expriedDate = new Date(now.getTime() - 1);
		return Jwts.builder()
			.claim(CLAIM_NAME, MEMBER_NO)
			.setIssuedAt(now)
			.setExpiration(expriedDate)
			.signWith(SECRET_KEY, SignatureAlgorithm.HS256)
			.compact();
	}
}
