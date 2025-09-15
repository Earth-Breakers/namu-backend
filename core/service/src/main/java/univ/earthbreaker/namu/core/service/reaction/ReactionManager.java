package univ.earthbreaker.namu.core.service.reaction;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import univ.earthbreaker.namu.core.domain.reaction.ReactionCommand;
import univ.earthbreaker.namu.core.domain.reaction.ReactionConflictException;
import univ.earthbreaker.namu.core.domain.reaction.infra.ReactionRepository;

@Component
public class ReactionManager {

	private final ReactionRepository reactionRepository;

	public ReactionManager(ReactionRepository reactionRepository) {
		this.reactionRepository = reactionRepository;
	}

	@Transactional
	public void doReaction(ReactionCommand reactionCommand) {
		if (reactionRepository.alreadyReaction(reactionCommand.toDbQuery())) {
			throw ReactionConflictException.conflict(reactionCommand.getTargetNo());
		}
		reactionRepository.reaction(reactionCommand.toDbCommand());
	}

	@Transactional
	public void undoReaction(ReactionCommand reactionCommand) {
		if (!reactionRepository.alreadyReaction(reactionCommand.toDbQuery())) {
			return;
		}
		reactionRepository.cancelReaction(reactionCommand.toDbCommand());
	}
}
