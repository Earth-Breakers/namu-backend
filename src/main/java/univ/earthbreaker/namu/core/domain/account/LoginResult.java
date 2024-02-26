package univ.earthbreaker.namu.core.domain.account;

public record LoginResult(
	String accessToken,
	String refreshToken,
	boolean isNewMember
) {
}
