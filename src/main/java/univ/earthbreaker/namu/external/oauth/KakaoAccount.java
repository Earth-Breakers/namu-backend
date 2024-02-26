package univ.earthbreaker.namu.external.oauth;

record KakaoAccount(Profile profile) {
	String profileNickname() {
		return profile.nickname();
	}
}
