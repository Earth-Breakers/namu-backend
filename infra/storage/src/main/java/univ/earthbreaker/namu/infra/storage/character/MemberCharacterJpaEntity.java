package univ.earthbreaker.namu.infra.storage.character;

import org.jetbrains.annotations.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import univ.earthbreaker.namu.core.domain.character.book.MemberCharacter;

@Entity
@Table(name = "member_character")
public class MemberCharacterJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;

	@Column(nullable = false)
	private Long memberNo;

	@Column(nullable = false)
	private Long characterNo;

	@Column(nullable = false)
	private Integer count;

	protected MemberCharacterJpaEntity() {
	}

	private MemberCharacterJpaEntity(Long memberNo, Long characterNo, Integer count) {
		this.memberNo = memberNo;
		this.characterNo = characterNo;
		this.count = count;
	}

	static @NotNull MemberCharacterJpaEntity initialize(Long memberNo, Long characterNo) {
		return new MemberCharacterJpaEntity(memberNo, characterNo, 1);
	}

	MemberCharacter toMemberCharacter(@NotNull CharacterJpaEntity characterJpaEntity, boolean isAcquired) {
		return new MemberCharacter(
			no,
			memberNo,
			count,
			characterJpaEntity.toNamuCharacter(),
			isAcquired
		);
	}

	void plusOneCount() {
		this.count++;
	}

	Long getCharacterNo() {
		return characterNo;
	}
}
