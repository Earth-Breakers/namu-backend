package univ.earthbreaker.namu.core.domain.point;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import univ.earthbreaker.namu.core.domain.common.SelfValidating;

public class ProvideEnergyPointCommand extends SelfValidating<ProvideEnergyPointCommand> {

	private static final String NOT_ALLOWED_ENERGY_TYPE = "DEFAULT";

	private final @NotNull Long memberNo;
	private final @NotNull Integer point;
	private final @NotNull @NotBlank String energyType;

	public ProvideEnergyPointCommand(Long memberNo, Integer point, String energyType) {
		this.memberNo = memberNo;
		this.point = point;
		this.energyType = energyType;
		this.validateEnergyType(energyType);
		this.validateSelf("memberNo, point 는 null 이 될 수 없고, energyType 는 null 혹은 공백일 수 없습니다");
	}

	private void validateEnergyType(String energyType) {
		if (energyType.equals(NOT_ALLOWED_ENERGY_TYPE)) {
			throw new IllegalArgumentException("캐릭터에게 제공할 에너지 타입은 DEFAULT 일 수 없습니다");
		}
	}

	public Long getMemberNo() {
		return memberNo;
	}

	public Integer getPoint() {
		return point;
	}

	public String getEnergyType() {
		return energyType;
	}
}
