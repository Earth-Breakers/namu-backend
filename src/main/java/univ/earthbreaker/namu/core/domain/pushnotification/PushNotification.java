package univ.earthbreaker.namu.core.domain.pushnotification;

import java.util.Objects;

public class PushNotification {

	private final Long no;
	private final Long memberNo;
	private final String token;
	private final boolean enable;

	public PushNotification(Long no, Long memberNo, String token, boolean enable) {
		this.no = no;
		this.memberNo = memberNo;
		this.token = token;
		this.enable = enable;
	}

	public boolean isNotSame(String token) {
		return !this.token.equals(token);
	}

	public PushNotification modifyToken(String newToken) {
		return new PushNotification(no, memberNo, newToken, enable);
	}

	public Long getNo() {
		return no;
	}

	public String getToken() {
		return token;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		PushNotification that = (PushNotification)o;
		return enable == that.enable && no.equals(that.no) && memberNo.equals(that.memberNo) && token.equals(
			that.token);
	}

	@Override
	public int hashCode() {
		return Objects.hash(no, memberNo, token, enable);
	}
}
