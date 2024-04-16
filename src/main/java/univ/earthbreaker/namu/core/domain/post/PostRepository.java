package univ.earthbreaker.namu.core.domain.post;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository {

	void create(PostCreateDbCommand command);

	@NotNull List<Post> findAll(PostDbQuery query);

	@Nullable Post find(PostDetailDbQuery query);
}
