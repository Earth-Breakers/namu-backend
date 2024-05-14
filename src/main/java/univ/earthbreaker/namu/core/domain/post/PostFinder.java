package univ.earthbreaker.namu.core.domain.post;

import java.time.LocalDate;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class PostFinder {

	private final PostRepository postRepository;

	public PostFinder(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public List<Post> findAll(long memberNo, LocalDate date) {
		return postRepository.findAll(new PostDbQuery(memberNo, date));
	}

	public @NotNull Post find(long memberNo, long postNo) {
		Post post = postRepository.find(new PostDetailDbQuery(memberNo, postNo));
		if (post != null) {
			return post;
		}
		throw PostNotFoundException.notFound();
	}

	public RelatedPostResult findAllRelated(@NotNull RelatedPostRetrieveQuery query) {
		return postRepository.findRelated(query.toDbQuery());
	}
}
