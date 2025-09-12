package univ.earthbreaker.namu.infra.storage.post;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import univ.earthbreaker.namu.core.domain.post.Post;
import univ.earthbreaker.namu.core.domain.post.PostCreateDbCommand;
import univ.earthbreaker.namu.core.domain.post.PostDbQuery;
import univ.earthbreaker.namu.core.domain.post.PostDetailDbQuery;
import univ.earthbreaker.namu.core.domain.post.PostRepository;
import univ.earthbreaker.namu.core.domain.post.RelatedPostDbQuery;
import univ.earthbreaker.namu.core.domain.post.RelatedPostResult;

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

	@Override
	public @NotNull RelatedPostResult findRelated(@NotNull RelatedPostDbQuery query) {
		PostSortKey postSortKey = new PostSortKey(query.sortKey());
		Pageable pageable = PageRequest.of(query.page(), query.size(), postSortKey.getSort());
		Slice<PostJpaEntity> postJpaEntities = postJpaRepository
			.findAllByMissionNoAndMemberNoNot(query.memberNo(), query.relatedMissionNo(), pageable);
		List<Post> posts = postJpaEntities.stream()
			.map(PostJpaEntity::toPost)
			.toList();
		return new RelatedPostResult(posts, postJpaEntities.isLast());
	}
}
