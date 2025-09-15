package univ.earthbreaker.namu.core.domain.account.infra;

public interface OAuthClientPort {

	OAuthMemberInfoResult getOAuthMemberInfo(String socialToken);

	record OAuthMemberInfoResult(
		String id,
		String nickname
	) {
	}
}
