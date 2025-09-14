package univ.earthbreaker.namu.core.domain.reaction.infra;

import java.util.List;

import univ.earthbreaker.namu.core.domain.reaction.Reaction;

public interface ReactionRepository {

	void reaction(ReactionDbCommand command);

	void cancelReaction(ReactionDbCommand command);

	boolean alreadyReaction(ReactionDbQuery query);

	List<Reaction> findReactions(ReactionDbQuery query);
}
