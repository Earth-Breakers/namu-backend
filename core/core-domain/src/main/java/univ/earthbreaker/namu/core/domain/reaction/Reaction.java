package univ.earthbreaker.namu.core.domain.reaction;

public class Reaction {

	private final long no;
	private final long memberNo;
	private final ReactionTarget reactionTarget;
	private final ReactionType reactionType;

	public Reaction(long no, long memberNo, long targetNo, TargetType targetType, ReactionType reactionType) {
		this.no = no;
		this.memberNo = memberNo;
		this.reactionTarget = new ReactionTarget(targetNo, targetType);
		this.reactionType = reactionType;
	}

	record ReactionTarget(long targetNo, TargetType targetType) {
	}

	long getMemberNo() {
		return memberNo;
	}

	ReactionType getReactionType() {
		return reactionType;
	}
}
