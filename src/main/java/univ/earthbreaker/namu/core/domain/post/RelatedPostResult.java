package univ.earthbreaker.namu.core.domain.post;

import java.util.List;

public record RelatedPostResult(
	List<Post> posts,
	boolean isLast
) {
}
