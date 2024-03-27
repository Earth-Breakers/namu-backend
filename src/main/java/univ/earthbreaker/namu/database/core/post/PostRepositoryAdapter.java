package univ.earthbreaker.namu.database.core.post;

import org.springframework.stereotype.Repository;

import univ.earthbreaker.namu.core.domain.post.PostCreateDbCommand;
import univ.earthbreaker.namu.core.domain.post.PostRepository;

@Repository
public class PostRepositoryAdapter implements PostRepository {

	private final PostJpaRepository postJpaRepository;

	public PostRepositoryAdapter(PostJpaRepository postJpaRepository) {
		this.postJpaRepository = postJpaRepository;
	}

	@Override
	public void create(PostCreateDbCommand command) {
		postJpaRepository.save(PostJpaEntity.from(command));
	}
}
