package univ.earthbreaker.namu.database.core.character;

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

	MemberCharacter toMemberCharacter(@NotNull CharacterJpaEntity characterJpaEntity) {
		return new MemberCharacter(
			no,
			memberNo,
			count,
			characterJpaEntity.toNamuCharacter()
		);
	}
}
