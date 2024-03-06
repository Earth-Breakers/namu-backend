package univ.earthbreaker.namu.database.core.character;

import org.jetbrains.annotations.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import univ.earthbreaker.namu.core.domain.character.CharacterType;
import univ.earthbreaker.namu.core.domain.character.CurrentCharacter;

@Entity
@Table(name = "current_character")
public class CurrentCharacterJpaEntity {

	private static final int INITIAL_EXP = 0;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;

	@Column(nullable = false)
	private Long memberNo;

	@Column(nullable = false)
	private Long characterNo;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private CharacterType characterType;

	@Column(nullable = false)
	private Integer groupNumber;

	@Column(nullable = false)
	private Integer level;

	@Column(nullable = false)
	private Integer exp;

	@Column(nullable = false)
	private Integer requiredExp;

	@Column(nullable = false, length = 500)
	private String mainImagePath;

	protected CurrentCharacterJpaEntity() {
	}

	private CurrentCharacterJpaEntity(
		Long memberNo,
		Long characterNo,
		CharacterType characterType,
		Integer groupNumber,
		Integer level,
		Integer exp,
		Integer requiredExp,
		String mainImagePath
	) {
		this.memberNo = memberNo;
		this.characterNo = characterNo;
		this.characterType = characterType;
		this.groupNumber = groupNumber;
		this.level = level;
		this.exp = exp;
		this.requiredExp = requiredExp;
		this.mainImagePath = mainImagePath;
	}

	static @NotNull CurrentCharacterJpaEntity initialize(
		@NotNull CharacterProjection initCharacterProjection,
		long memberNo
	) {
		return new CurrentCharacterJpaEntity(
			memberNo,
			initCharacterProjection.getNo(),
			initCharacterProjection.getType(),
			initCharacterProjection.getGroupNumber(),
			initCharacterProjection.getLevel(),
			INITIAL_EXP,
			initCharacterProjection.getRequiredExp(),
			initCharacterProjection.getMainImagePath()
		);
	}

	CurrentCharacter toCurrentCharacter() {
		return CurrentCharacter.of(
			memberNo,
			characterNo,
			characterType,
			level,
			requiredExp,
			exp,
			groupNumber,
			mainImagePath
		);
	}

	CurrentCharacter toNextCurrentCharacter() {
		return CurrentCharacter.createNext(
			memberNo,
			characterNo,
			characterType,
			level,
			requiredExp,
			groupNumber,
			mainImagePath
		);
	}

	CurrentCharacter toInitCurrentCharacter() {
		return CurrentCharacter.initialize(
			memberNo,
			characterNo,
			requiredExp,
			groupNumber,
			mainImagePath
		);
	}

	Long getMemberNo() {
		return memberNo;
	}

	Long getCharacterNo() {
		return characterNo;
	}

	Integer getLevel() {
		return level;
	}

	Integer getRequiredExp() {
		return requiredExp;
	}

	String getMainImagePath() {
		return mainImagePath;
	}
}
