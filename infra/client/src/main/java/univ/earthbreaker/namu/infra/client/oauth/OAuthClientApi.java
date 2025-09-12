package univ.earthbreaker.namu.infra.client.oauth;

public interface OAuthClientApi {
	OAuthMemberInfoResult getOAuthMemberInfo(String socialToken);
}
