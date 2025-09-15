package univ.earthbreaker.namu.core.domain.post.infra;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import univ.earthbreaker.namu.core.domain.post.Post;
import univ.earthbreaker.namu.core.domain.post.RelatedPostResult;

public interface PostRepository {

	void create(PostCreateDbCommand command);

	@NotNull List<Post> findAll(PostDbQuery query);

	@Nullable Post find(PostDetailDbQuery query);

	@NotNull RelatedPostResult findRelated(RelatedPostDbQuery query);
}
