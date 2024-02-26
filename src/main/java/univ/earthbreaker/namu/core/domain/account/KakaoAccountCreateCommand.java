package univ.earthbreaker.namu.core.domain.account;

public class KakaoAccountCreateCommand extends AccountCreateCommand {

	protected KakaoAccountCreateCommand(String socialId, Long memberNo) {
		super(socialId, SocialType.KAKAO, memberNo);
	}
}
