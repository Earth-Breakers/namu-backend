package univ.earthbreaker.namu.core.storage.reaction;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import univ.earthbreaker.namu.core.domain.reaction.Reaction;
import univ.earthbreaker.namu.core.domain.reaction.ReactionDbCommand;
import univ.earthbreaker.namu.core.domain.reaction.ReactionDbQuery;
import univ.earthbreaker.namu.core.domain.reaction.ReactionRepository;

@Repository
public class ReactionRepositoryAdapter implements ReactionRepository {

	private final ReactionJpaRepository reactionJpaRepository;

	public ReactionRepositoryAdapter(ReactionJpaRepository reactionJpaRepository) {
		this.reactionJpaRepository = reactionJpaRepository;
	}

	@Override
	public void reaction(@NotNull ReactionDbCommand command) {
		ReactionJpaEntity reactionJpaEntity = ReactionJpaEntity.create(
			command.memberNo(),
			command.targetNo(),
			command.targetType(),
			command.reactionType()
		);
		reactionJpaRepository.save(reactionJpaEntity);
	}

	@Override
	public void cancelReaction(@NotNull ReactionDbCommand command) {
		reactionJpaRepository.deleteByMemberNoAndReactionTarget(
			command.memberNo(),
			new ReactionJpaEntity.ReactionTarget(
				command.targetNo(),
				command.targetType()
			)
		);
	}

	@Override
	public boolean alreadyReaction(@NotNull ReactionDbQuery query) {
		return reactionJpaRepository.existsByMemberNoAndReactionTarget(
			query.memberNo(),
			new ReactionJpaEntity.ReactionTarget(
				query.targetNo(),
				query.targetType()
			)
		);
	}

	@Override
	public List<Reaction> findReactions(@NotNull ReactionDbQuery query) {
		return reactionJpaRepository.findAllByMemberNoAndReactionTarget(
				query.memberNo(),
				new ReactionJpaEntity.ReactionTarget(
					query.targetNo(),
					query.targetType()
				)
			)
			.stream()
			.map(ReactionJpaEntity::toReaction)
			.toList();
	}
}
