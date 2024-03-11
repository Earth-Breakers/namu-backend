package univ.earthbreaker.namu.database.core;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "member_character_book")
public class MemberCharacterBookJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;

	@Column(nullable = false)
	private Long memberNo;

	@Column(nullable = false)
	private Long characterNo;

	@Column(nullable = false)
	private Integer count;

	protected MemberCharacterBookJpaEntity() {
	}
}
