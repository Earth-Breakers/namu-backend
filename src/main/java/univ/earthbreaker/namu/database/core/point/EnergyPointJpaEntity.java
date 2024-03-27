package univ.earthbreaker.namu.database.core.point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "energy_point")
public class EnergyPointJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;

	@Column(nullable = false)
	private Integer point;

	@Column(nullable = false)
	private Long memberNo;

	protected EnergyPointJpaEntity() {
	}
}
