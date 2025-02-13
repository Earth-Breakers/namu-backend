package univ.earthbreaker.namu.core.domain.point;

import jakarta.validation.constraints.NotNull;
import univ.earthbreaker.namu.core.domain.common.SelfValidating;

public class EnergyGiftCommand extends SelfValidating<EnergyGiftCommand> {

	private final @NotNull Long memberNo;
	private final @NotNull Long targetMemberNo;
	private final @NotNull Integer pointValue;

	public EnergyGiftCommand(Long memberNo, Long targetMemberNo, Integer pointValue) {
		this.memberNo = memberNo;
		this.targetMemberNo = targetMemberNo;
		this.pointValue = pointValue;
		this.validateSelf("memberNo, targetMemberNo, pointValue 는 null 이 될 수 없습니다");
	}

	Long getMemberNo() {
		return memberNo;
	}

	Long getTargetMemberNo() {
		return targetMemberNo;
	}

	Integer getPointValue() {
		return pointValue;
	}
}
