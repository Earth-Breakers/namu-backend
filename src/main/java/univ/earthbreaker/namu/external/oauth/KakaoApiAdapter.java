package univ.earthbreaker.namu.external.oauth;

import org.springframework.stereotype.Component;

@Component
public class KakaoApiAdapter implements OAuthClientApi {

	private final KakaoApiCaller kakaoApiCaller;

	public KakaoApiAdapter(KakaoApiCaller kakaoApiCaller) {
		this.kakaoApiCaller = kakaoApiCaller;
	}

	@Override
	public OAuthMemberInfoResult getOAuthMemberInfo(String socialToken) {
		return kakaoApiCaller.getKakaoUserInfo("Bearer " + socialToken).toResult();
	}
}
