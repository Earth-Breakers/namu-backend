package univ.earthbreaker.namu.database.core.post;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

import univ.earthbreaker.namu.core.domain.post.Post;
import univ.earthbreaker.namu.core.domain.post.PostCreateDbCommand;
import univ.earthbreaker.namu.core.domain.post.PostDbQuery;
import univ.earthbreaker.namu.core.domain.post.PostDetailDbQuery;
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

	@Override
	public @NotNull List<Post> findAll(@NotNull PostDbQuery query) {
		LocalDate date = query.date();
		LocalDateTime startDate = date.atStartOfDay();
		LocalDateTime endDate = date.plusDays(1).atStartOfDay();

		return postJpaRepository.findAllByMemberNoAndCreatedAtBetween(query.memberNo(), startDate, endDate)
			.stream()
			.map(PostJpaEntity::toPost)
			.toList();
	}

	@Override
	public @Nullable Post find(@NotNull PostDetailDbQuery query) {
		PostJpaEntity postJpaEntity = postJpaRepository.findByNoAndMemberNo(query.postNo(), query.memberNo());
		if (postJpaEntity != null) {
			return postJpaEntity.toPost();
		}
		return null;
	}
}
