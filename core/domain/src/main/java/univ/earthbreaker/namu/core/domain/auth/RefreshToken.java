package univ.earthbreaker.namu.core.domain.auth;

import java.time.LocalDateTime;
import java.util.Objects;

public class RefreshToken {

	private final Long no;
	private final String value;
	private final LocalDateTime expiresIn;
	private final long memberNo;

	public RefreshToken(Long no, String value, LocalDateTime expiresIn, long memberNo) {
		this.no = no;
		this.value = value;
		this.expiresIn = expiresIn;
		this.memberNo = memberNo;
	}

	public boolean isExpired(LocalDateTime current) {
		return expiresIn.isBefore(current);
	}

	public static RefreshToken create(String refreshTokenValue, LocalDateTime expiresIn, long memberNo) {
		return new RefreshToken(null, refreshTokenValue, expiresIn, memberNo);
	}

	public String getValue() {
		return value;
	}

	public LocalDateTime getExpiresIn() {
		return expiresIn;
	}

	public Long getMemberNo() {
		return memberNo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		RefreshToken that = (RefreshToken)o;
		return memberNo == that.memberNo && no.equals(that.no) && value.equals(that.value) && expiresIn.equals(
			that.expiresIn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(no, value, expiresIn, memberNo);
	}
}
