package univ.earthbreaker.namu.core.domain.reaction;

import org.springframework.stereotype.Service;

@Service
public class ReactionService {

	private final ReactionManager reactionManager;

	public ReactionService(ReactionManager reactionManager) {
		this.reactionManager = reactionManager;
	}

	public void doReaction(ReactionCommand command) {
		reactionManager.doReaction(command);
	}

	public void undoReaction(ReactionCommand command) {
		reactionManager.undoReaction(command);
	}
}
