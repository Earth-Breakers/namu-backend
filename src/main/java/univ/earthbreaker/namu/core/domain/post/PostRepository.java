package univ.earthbreaker.namu.core.domain.post;

import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository {

	void create(PostCreateDbCommand command);
}
