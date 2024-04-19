package univ.earthbreaker.namu.database.core.reaction;

import org.jetbrains.annotations.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import univ.earthbreaker.namu.core.domain.reaction.Reaction;
import univ.earthbreaker.namu.core.domain.reaction.ReactionType;
import univ.earthbreaker.namu.core.domain.reaction.TargetType;
import univ.earthbreaker.namu.database.core.common.BaseTimeJpaEntity;

@Entity
@Table(name = "reaction")
public class ReactionJpaEntity extends BaseTimeJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;

	@Column(nullable = false)
	private Long memberNo;

	@Embedded
	private ReactionTarget reactionTarget;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ReactionType reactionType;

	protected ReactionJpaEntity() {
	}

	private ReactionJpaEntity(Long memberNo, ReactionTarget reactionTarget, ReactionType reactionType) {
		this.memberNo = memberNo;
		this.reactionTarget = reactionTarget;
		this.reactionType = reactionType;
	}

	Reaction toReaction() {
		return new Reaction(no, memberNo, reactionTarget.targetNo, reactionTarget.targetType, reactionType);
	}

	static @NotNull ReactionJpaEntity create(
		long memberNo,
		long targetNo,
		TargetType targetType,
		ReactionType reactionType
	) {
		return new ReactionJpaEntity(memberNo, new ReactionTarget(targetNo, targetType), reactionType);
	}

	@Embeddable
	static class ReactionTarget {

		@Column(nullable = false)
		private Long targetNo;

		@Enumerated(EnumType.STRING)
		@Column(nullable = false)
		private TargetType targetType;

		protected ReactionTarget() {
		}

		ReactionTarget(Long targetNo, TargetType targetType) {
			this.targetNo = targetNo;
			this.targetType = targetType;
		}
	}
}
