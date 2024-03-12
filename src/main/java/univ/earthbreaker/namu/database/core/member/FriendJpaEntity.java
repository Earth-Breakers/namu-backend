package univ.earthbreaker.namu.database.core.member;

import org.jetbrains.annotations.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "friend")
public class FriendJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;

	@Column(nullable = false)
	private Long masterMemberNo;

	@Column(nullable = false)
	private Long targetMemberNo;

	protected FriendJpaEntity() {
	}

	private FriendJpaEntity(Long masterMemberNo, Long targetMemberNo) {
		this.masterMemberNo = masterMemberNo;
		this.targetMemberNo = targetMemberNo;
	}

	static @NotNull FriendJpaEntity create(long memberNo, long targetMemberNo) {
		return new FriendJpaEntity(memberNo, targetMemberNo);
	}
}
