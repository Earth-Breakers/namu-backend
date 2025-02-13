package univ.earthbreaker.namu.core.domain.mission;

import jakarta.validation.constraints.NotNull;
import univ.earthbreaker.namu.core.domain.common.SelfValidating;

public class MissionCompleteCommand extends SelfValidating<MissionCompleteCommand> {

	private final @NotNull Long memberNo;
	private final @NotNull Long missionNo;

	public MissionCompleteCommand(long memberNo, long missionNo) {
		this.memberNo = memberNo;
		this.missionNo = missionNo;
		this.validateSelf("memberNo 와 missionNo 는 null 이 될 수 없습니다");
	}

	public long getMemberNo() {
		return memberNo;
	}

	public long getMissionNo() {
		return missionNo;
	}
}
