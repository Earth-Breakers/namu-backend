package univ.earthbreaker.namu.infra.client.oauth;

record KakaoAccount(Profile profile) {
	String profileNickname() {
		return profile.nickname();
	}
}
