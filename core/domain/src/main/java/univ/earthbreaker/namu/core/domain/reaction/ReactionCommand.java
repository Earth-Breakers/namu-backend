package univ.earthbreaker.namu.core.domain.reaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import univ.earthbreaker.namu.core.domain.common.SelfValidating;
import univ.earthbreaker.namu.core.domain.reaction.infra.ReactionDbCommand;
import univ.earthbreaker.namu.core.domain.reaction.infra.ReactionDbQuery;

public class ReactionCommand extends SelfValidating<ReactionCommand> {

	private final @NotNull Long memberNo;
	private final @NotNull Long targetNo;
	private final @NotNull @NotBlank String targetType;
	private final @NotNull @NotBlank String reactionType;

	public ReactionCommand(Long memberNo, Long targetNo, String targetType, String reactionType) {
		this.memberNo = memberNo;
		this.targetNo = targetNo;
		this.targetType = targetType;
		this.reactionType = reactionType;
		this.validateSelf("targetNo, memberNo 는 null 이 될 수 없고, targetType, reactionType 은 null 이나 공백이 될 수 없습니다");
	}

	public ReactionDbCommand toDbCommand() {
		return new ReactionDbCommand(
			memberNo,
			targetNo,
			TargetType.valueOf(targetType),
			ReactionType.valueOf(reactionType)
		);
	}

	public ReactionDbQuery toDbQuery() {
		return new ReactionDbQuery(
			memberNo,
			targetNo,
			TargetType.valueOf(targetType)
		);
	}

	public Long getTargetNo() {
		return targetNo;
	}
}
