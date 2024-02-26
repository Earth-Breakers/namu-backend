package univ.earthbreaker.namu.database.auth;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import univ.earthbreaker.namu.core.auth.RefreshToken;

@Entity
@Table(name = "refresh_token")
public class RefreshTokenJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;

	@Column(nullable = false)
	private String token;

	@Column(nullable = false)
	private LocalDateTime expiresIn;

	@Column(nullable = false)
	private Long memberNo;

	protected RefreshTokenJpaEntity() {
	}

	public RefreshTokenJpaEntity(String token, LocalDateTime expiresIn, Long memberNo) {
		this.token = token;
		this.expiresIn = expiresIn;
		this.memberNo = memberNo;
	}

	RefreshToken toRefreshToken() {
		return new RefreshToken(no, token, expiresIn, memberNo);
	}
}
