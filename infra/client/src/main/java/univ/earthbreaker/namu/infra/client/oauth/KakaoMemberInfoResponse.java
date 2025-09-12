package univ.earthbreaker.namu.external.oauth;

import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record KakaoMemberInfoResponse(String id, KakaoAccount kakaoAccount) {

	@NotNull OAuthMemberInfoResult toResult() {
		return new OAuthMemberInfoResult(id, kakaoAccount().profileNickname());
	}
}
