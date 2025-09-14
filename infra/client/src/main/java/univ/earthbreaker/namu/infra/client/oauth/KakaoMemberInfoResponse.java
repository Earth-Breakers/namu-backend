package univ.earthbreaker.namu.infra.client.oauth;

import static univ.earthbreaker.namu.core.domain.account.infra.OAuthClientPort.OAuthMemberInfoResult;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record KakaoMemberInfoResponse(String id, KakaoAccount kakaoAccount) {

	OAuthMemberInfoResult toResult() {
		return new OAuthMemberInfoResult(id, kakaoAccount().profileNickname());
	}
}
