package univ.earthbreaker.namu.database.core;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import univ.earthbreaker.namu.core.domain.character.CharacterType;

@Entity
@Table(name = "current_character")
public class CurrentCharacterJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;

	@Column(nullable = false)
	private Long memberNo;

	@Column(nullable = false)
	private Long characterNo;

	@Column(nullable = false)
	private Integer exp;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private CharacterType energyType;

	protected CurrentCharacterJpaEntity() {
	}
}
