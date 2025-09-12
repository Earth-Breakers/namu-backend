package univ.earthbreaker.namu.core.storage.point;

import org.jetbrains.annotations.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import univ.earthbreaker.namu.core.domain.point.Energy;

@Entity
@Table(name = "energy_point")
public class EnergyPointJpaEntity {

	private static final int INITIALIZE_POINT = 0;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;

	@Column(nullable = false)
	private Integer point;

	@Column(nullable = false)
	private Long memberNo;

	protected EnergyPointJpaEntity() {
	}

	private EnergyPointJpaEntity(Integer point, Long memberNo) {
		this.point = point;
		this.memberNo = memberNo;
	}

	static @NotNull EnergyPointJpaEntity initialize(long memberNo) {
		return new EnergyPointJpaEntity(INITIALIZE_POINT, memberNo);
	}

	Energy toEnergy() {
		return Energy.of(no, memberNo, point);
	}
}
