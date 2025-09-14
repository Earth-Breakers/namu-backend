package univ.earthbreaker.namu.infra.client.oauth;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.account.infra.OAuthClientPort;

@Component
class KakaoPortAdapter implements OAuthClientPort {

	private final KakaoApiCaller kakaoApiCaller;

	public KakaoPortAdapter(KakaoApiCaller kakaoApiCaller) {
		this.kakaoApiCaller = kakaoApiCaller;
	}

	@Override
	public OAuthMemberInfoResult getOAuthMemberInfo(String socialToken) {
		return kakaoApiCaller.getKakaoUserInfo("Bearer " + socialToken).toResult();
	}
}
