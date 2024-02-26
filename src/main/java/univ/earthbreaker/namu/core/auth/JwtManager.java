package univ.earthbreaker.namu.core.auth;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;

@Component
public class JwtManager {

	private static final String JWT_CLAIM_MEMBER_NO = "MEMBER_NO";

	private final Key secretKey;
	private final long accessTokenValidDuration;
	private final long refreshTokenValidDuration;

	public JwtManager(
		@Value("${token.jwt.secret}") String secretKey,
		@Value("${token.jwt.access-valid-duration}") long accessTokenValidDuration,
		@Value("${token.jwt.refresh-valid-duration}") long refreshTokenValidDuration
	) {
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
		this.accessTokenValidDuration = accessTokenValidDuration;
		this.refreshTokenValidDuration = refreshTokenValidDuration;
	}

	String createAccessToken(Object payload) {
		Date now = new Date();
		Map<String, Object> claims = new HashMap<>();
		claims.put(JWT_CLAIM_MEMBER_NO, payload);

		return Jwts.builder()
			.addClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + accessTokenValidDuration))
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();
	}

	RefreshToken createRefreshToken(Long memberNo) {
		Date now = new Date();
		Date expiredIn = new Date(now.getTime() + refreshTokenValidDuration);

		String refreshTokenValue = Jwts.builder()
			.setIssuedAt(now)
			.setExpiration(expiredIn)
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();

		long days = TimeUnit.MICROSECONDS.toDays(refreshTokenValidDuration);

		return RefreshToken.create(refreshTokenValue, LocalDateTime.now().plusDays(days), memberNo);
	}

	public void validate(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token);
		} catch (SecurityException | MalformedJwtException | IllegalArgumentException | UnsupportedJwtException e) {
			throw UnAuthorizedException.wrong(token);
		} catch (ExpiredJwtException e) {
			throw UnAuthorizedException.expired(token);
		}
	}

	public Object getPayload(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.get(JWT_CLAIM_MEMBER_NO, Long.class);
	}
}
