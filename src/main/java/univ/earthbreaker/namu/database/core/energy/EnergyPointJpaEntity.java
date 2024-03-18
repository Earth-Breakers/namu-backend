package univ.earthbreaker.namu.database.core.energy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import univ.earthbreaker.namu.core.domain.energy.Energy;

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

	public EnergyPointJpaEntity(Integer point, Long memberNo) {
		this.point = point;
		this.memberNo = memberNo;
	}

	Energy toEnergy() {
		return Energy.of(no, memberNo, point);
	}
}
