package univ.earthbreaker.namu.external.oauth;

public class OAuthClientException extends RuntimeException {

	private OAuthClientException(String message) {
		super(message);
	}

	public static OAuthClientException unauthorized() {
		return new OAuthClientException("kakao social access token 이 만료되었습니다");
	}

	public static OAuthClientException another() {
		return new OAuthClientException("kakao client 의 알 수 없는 에러로 인증에 실패했습니다");
	}
}
