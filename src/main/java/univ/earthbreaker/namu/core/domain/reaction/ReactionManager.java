package univ.earthbreaker.namu.core.domain.reaction;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ReactionManager {

	private final ReactionRepository reactionRepository;

	public ReactionManager(ReactionRepository reactionRepository) {
		this.reactionRepository = reactionRepository;
	}

	@Transactional
	public void doReaction(@NotNull ReactionCommand reactionCommand) {
		if (reactionRepository.alreadyReaction(reactionCommand.toDbQuery())) {
			throw ReactionConflictException.conflict(reactionCommand.getTargetNo());
		}
		reactionRepository.reaction(reactionCommand.toDbCommand());
	}

	@Transactional
	public void undoReaction(@NotNull ReactionCommand reactionCommand) {
		if (!reactionRepository.alreadyReaction(reactionCommand.toDbQuery())) {
			return;
		}
		reactionRepository.cancelReaction(reactionCommand.toDbCommand());
	}
}
