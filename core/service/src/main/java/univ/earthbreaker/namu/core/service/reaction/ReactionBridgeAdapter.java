package univ.earthbreaker.namu.core.domain.reaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.reaction.infra.ReactionDbQuery;
import univ.earthbreaker.namu.core.domain.reaction.infra.ReactionRepository;

@Component
public class ReactionBridgeAdapter implements ReactionBridge {

	private final ReactionRepository reactionRepository;

	public ReactionBridgeAdapter(ReactionRepository reactionRepository) {
		this.reactionRepository = reactionRepository;
	}

	@Override
	public ReactionStatus find(long memberNo, long targetNo, String targetTypeName) {
		ReactionDbQuery reactionDbQuery = new ReactionDbQuery(memberNo, targetNo, TargetType.valueOf(targetTypeName));
		Reactions reactions = new Reactions(reactionRepository.findReactions(reactionDbQuery));

		Map<ReactionType, Reactions> reactionTypeReactionsMap = reactions.groupingByReactionType();
		List<ReactionStatus.Status> reactionStatuses = new ArrayList<>();
		for (Map.Entry<ReactionType, Reactions> reactionsEntry : reactionTypeReactionsMap.entrySet()) {
			String reactionTypeName = reactionsEntry.getKey().name();
			int reactionCount = reactionsEntry.getValue().getReactionCount();
			List<Long> reactionMembers = reactionsEntry.getValue().getReactionMembers();
			reactionStatuses.add(new ReactionStatus.Status(reactionTypeName, reactionCount, reactionMembers));
		}
		boolean alreadyReaction = reactionRepository.alreadyReaction(reactionDbQuery);

		return new ReactionStatus(reactionStatuses, alreadyReaction);
	}
}
