package univ.earthbreaker.namu.external.oauth;

public interface OAuthClientApi {
	OAuthMemberInfoResult getOAuthMemberInfo(String socialToken);
}
