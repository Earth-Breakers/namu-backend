package univ.earthbreaker.namu.core.domain.pushnotification;

public class PushNotification {

	private final long no;
	private final long memberNo;
	private final String token;
	private final boolean enable;

	public PushNotification(long no, long memberNo, String token, boolean enable) {
		this.no = no;
		this.memberNo = memberNo;
		this.token = token;
		this.enable = enable;
	}

	boolean isNotSame(String token) {
		return !this.token.equals(token);
	}

	PushNotification modifyToken(String newToken) {
		return new PushNotification(no, memberNo, newToken, enable);
	}

	public long getNo() {
		return no;
	}

	public String getToken() {
		return token;
	}

	public boolean isEnable() {
		return enable;
	}
}
