package univ.earthbreaker.namu.db.core.character;

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
import univ.earthbreaker.namu.core.domain.character.current.CurrentCharacter;

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
	private Integer currentExp;

	@Column(nullable = false)
	private Integer requiredExp;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, length = 500)
	private String mainImagePath;

	@Column(nullable = false, length = 500)
	private String backgroundImagePath;

	@Column(nullable = false, length = 500)
	private String scripts;

	protected CurrentCharacterJpaEntity() {
	}

	private CurrentCharacterJpaEntity(
		Long memberNo,
		Long characterNo,
		CharacterType characterType,
		Integer groupNumber,
		Integer level,
		Integer currentExp,
		Integer requiredExp,
		String name,
		String mainImagePath,
		String backgroundImagePath,
		String scripts
	) {
		this.memberNo = memberNo;
		this.characterNo = characterNo;
		this.characterType = characterType;
		this.groupNumber = groupNumber;
		this.level = level;
		this.currentExp = currentExp;
		this.requiredExp = requiredExp;
		this.name = name;
		this.mainImagePath = mainImagePath;
		this.backgroundImagePath = backgroundImagePath;
		this.scripts = scripts;
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
			initCharacterProjection.getName(),
			initCharacterProjection.getMainImagePath(),
			initCharacterProjection.getBackgroundImagePath(),
			initCharacterProjection.getScripts()
		);
	}

	CurrentCharacter toCurrentCharacter() {
		return CurrentCharacter.of(
			memberNo,
			characterNo,
			characterType,
			level,
			requiredExp,
			currentExp,
			groupNumber,
			name,
			mainImagePath,
			backgroundImagePath,
			scripts
		);
	}

	CurrentCharacter toInitCurrentCharacter() {
		return CurrentCharacter.
			initialize(
				memberNo,
				characterNo,
				requiredExp,
				groupNumber,
				name,
				mainImagePath,
				backgroundImagePath,
				scripts
			);
	}

	Long getMemberNo() {
		return memberNo;
	}

	Long getCharacterNo() {
		return characterNo;
	}

	CharacterType getCharacterType() {
		return characterType;
	}

	Integer getLevel() {
		return level;
	}

	Integer getRequiredExp() {
		return requiredExp;
	}

	Integer getCurrentExp() {
		return currentExp;
	}

	String getMainImagePath() {
		return mainImagePath;
	}

	String getBackgroundImagePath() {
		return backgroundImagePath;
	}

	String getScripts() {
		return scripts;
	}
}
