package univ.earthbreaker.namu.core.domain.reaction;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository {

	void reaction(ReactionDbCommand command);

	void cancelReaction(ReactionDbCommand command);

	boolean alreadyReaction(ReactionDbQuery query);

	List<Reaction> findReactions(ReactionDbQuery query);
}
