package univ.earthbreaker.namu.database.core.member;

import org.jetbrains.annotations.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import univ.earthbreaker.namu.core.domain.member.MemberStatus;

@Entity
@Table(name = "member")
public class MemberJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;

	@Column(nullable = false, unique = true, length = 20)
	private String nickname;

	@Column(nullable = false)
	private Integer level;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private MemberStatus status;

	protected MemberJpaEntity() {
	}

	public MemberJpaEntity(String nickname, Integer level, MemberStatus status) {
		this.nickname = nickname;
		this.level = level;
		this.status = status;
	}

	static @NotNull MemberJpaEntity create(String socialNickname) {
		return new MemberJpaEntity(socialNickname, 1, MemberStatus.ACTIVE);
	}

	public Long getNo() {
		return no;
	}
}