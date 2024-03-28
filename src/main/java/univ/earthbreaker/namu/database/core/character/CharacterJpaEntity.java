package univ.earthbreaker.namu.database.core.character;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import univ.earthbreaker.namu.core.domain.character.CharacterType;
import univ.earthbreaker.namu.core.domain.character.Gender;
import univ.earthbreaker.namu.core.domain.character.NamuCharacter;

@Entity
@Table(name = "namu_character")
public class CharacterJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private CharacterType type;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private Gender gender;

	@Column(nullable = false)
	private Boolean isEndangered;

	@Column(nullable = false)
	private Integer groupNumber;

	@Column(nullable = false)
	private Integer level;

	@Column(nullable = false)
	private Integer requiredExp;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, length = 500)
	private String description;

	@Column(nullable = false, length = 500)
	private String thumbnailImagePath;

	@Column(nullable = false, length = 500)
	private String mainImagePath;

	protected CharacterJpaEntity() {
	}

	NamuCharacter toNamuCharacter() {
		return NamuCharacter.builder()
			.no(no)
			.type(type)
			.gender(gender)
			.isEndangered(isEndangered)
			.groupNumber(groupNumber)
			.level(level)
			.requiredExp(requiredExp)
			.name(name)
			.description(description)
			.thumbnailImagePath(thumbnailImagePath)
			.mainImagePath(mainImagePath)
			.build();
	}
}
