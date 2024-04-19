package univ.earthbreaker.namu.core.domain.reaction;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Reactions {

	private final List<Reaction> values;

	public Reactions(List<Reaction> values) {
		this.values = values;
	}

	Map<ReactionType, Reactions> groupingByReactionType() {
		return values.stream()
			.collect(Collectors.groupingBy(
				Reaction::getReactionType,
				Collectors.collectingAndThen(Collectors.toList(), Reactions::new)
			));
	}

	List<Long> getReactionMembers() {
		return values.stream()
			.map(Reaction::getMemberNo)
			.toList();
	}

	int getReactionCount() {
		return values.size();
	}
}
