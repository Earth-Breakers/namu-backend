package univ.earthbreaker.namu.core.domain.point;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import univ.earthbreaker.namu.core.domain.common.SelfValidating;

public class ProvideEnergyPointCommand extends SelfValidating<ProvideEnergyPointCommand> {

	private final @NotNull Long memberNo;
	private final @NotNull Integer point;
	private final @NotBlank String energyType;

	public ProvideEnergyPointCommand(Long memberNo, Integer point, String energyType) {
		this.memberNo = memberNo;
		this.point = point;
		this.energyType = energyType;
		this.validateSelf("memberNo, point 는 null 이 될 수 없고, energyType 는 공백일 수 없습니다");
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
