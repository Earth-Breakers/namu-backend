package univ.earthbreaker.namu.database.core.account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import univ.earthbreaker.namu.core.domain.account.Account;
import univ.earthbreaker.namu.core.domain.account.SocialType;

@Entity
@Table(name = "account")
public class AccountJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;

	@Column(nullable = false, length = 100)
	private String socialId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private SocialType socialType;

	@Column(nullable = false)
	private Long memberNo;

	protected AccountJpaEntity() {
	}

	public AccountJpaEntity(String socialId, SocialType socialType, Long memberNo) {
		this.socialId = socialId;
		this.socialType = socialType;
		this.memberNo = memberNo;
	}

	Account toAccount() {
		return new Account(no, socialId, socialType, memberNo);
	}
}
