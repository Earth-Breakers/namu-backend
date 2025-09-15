package univ.earthbreaker.namu.core.service.account;

public interface TokenManager {

	String createAccessToken(Object payload);

	String createRefreshToken(Long memberNo);

	String updateRefreshToken(Long memberNo);
}
