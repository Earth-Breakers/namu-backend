package univ.earthbreaker.namu.core.domain.post;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class PostRetrieveService {

	private final PostFinder postFinder;

	public PostRetrieveService(PostFinder postFinder) {
		this.postFinder = postFinder;
	}

	public List<Post> retrieveAll(@NotNull PostRetrieveAllQuery query) {
		return postFinder.findAll(query.getMemberNo(), query.getDate());
	}

	public Post retrieve(@NotNull PostRetrieveDetailQuery query) {
		return postFinder.find(query.getMemberNo(), query.getPostNo());
	}
}
