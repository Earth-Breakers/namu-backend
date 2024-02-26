package univ.earthbreaker.namu.external.oauth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class KakaoApiAdapterTest {

	private static final String ID = "socialId";
	private static final String NICKNAME = "socialNickname";

	private final KakaoApiCaller kakaoApiCaller = new KakaoApiCallerImpl();
	private final KakaoApiAdapter kakaoApiAdapter = new KakaoApiAdapter(kakaoApiCaller);

	@DisplayName("socialToken 를 받아 OAuth 회원의 정보를 가져온다")
	@Test
	void getOAuthMemberInfo() {
		// when
		OAuthMemberInfoResult oAuthMemberInfoResult = kakaoApiAdapter.getOAuthMemberInfo("socialToken");

		// then
		assertAll(
			() -> assertThat(oAuthMemberInfoResult.id()).isEqualTo(ID),
			() -> assertThat(oAuthMemberInfoResult.nickname()).isEqualTo(NICKNAME)
		);
	}

	private static class KakaoApiCallerImpl implements KakaoApiCaller {

		@Override
		public @Nullable KakaoMemberInfoResponse getKakaoUserInfo(String bearerAccessToken) {
			return new KakaoMemberInfoResponse(
				ID,
				new KakaoAccount(
					new Profile(NICKNAME)
				)
			);
		}
	}
}
