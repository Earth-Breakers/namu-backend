package univ.earthbreaker.namu.core.service.post;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import univ.earthbreaker.namu.core.domain.post.Post;
import univ.earthbreaker.namu.core.domain.post.PostRetrieveAllQuery;
import univ.earthbreaker.namu.core.domain.post.RelatedPostResult;
import univ.earthbreaker.namu.core.domain.post.RelatedPostRetrieveQuery;

@Service
public class PostRetrieveService {

	private final PostFinder postFinder;

	public PostRetrieveService(PostFinder postFinder) {
		this.postFinder = postFinder;
	}

	public List<Post> retrieveAll(PostRetrieveAllQuery query) {
		return postFinder.findAll(query.getMemberNo(), query.getDate());
	}

	public RelatedPostResult retrieveRelated(RelatedPostRetrieveQuery query) {
		return postFinder.findAllRelated(query);
	}
}
