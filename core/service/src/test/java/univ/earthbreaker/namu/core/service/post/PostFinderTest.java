package univ.earthbreaker.namu.core.service.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import univ.earthbreaker.namu.core.domain.post.Post;
import univ.earthbreaker.namu.core.domain.post.PostNotFoundException;
import univ.earthbreaker.namu.core.domain.post.RelatedPostResult;
import univ.earthbreaker.namu.core.domain.post.RelatedPostRetrieveQuery;
import univ.earthbreaker.namu.core.domain.post.infra.PostDbQuery;
import univ.earthbreaker.namu.core.domain.post.infra.PostDetailDbQuery;
import univ.earthbreaker.namu.core.domain.post.infra.PostRepository;
import univ.earthbreaker.namu.core.domain.post.infra.RelatedPostDbQuery;

@ExtendWith(MockitoExtension.class)
class PostFinderTest {

	private @Mock PostRepository postRepository;
	private @InjectMocks PostFinder postFinder;

	@DisplayName("회원이 선택한 날짜에 성공한 미션에 대한 게시글들을 모두 조회한다")
	@Test
	void findAll() {
	    // given
		when(postRepository.findAll(any(PostDbQuery.class)))
			.thenReturn(List.of(PostFixture.FIRST_POST, PostFixture.SECOND_POST));

	    // when
		List<Post> actual = postFinder.findAll(PostFixture.MEMBER_NO, PostFixture.SEARCH_DATE);

		// then
		assertThat(actual).isNotNull().hasSize(2);
	}

	@DisplayName("회원의 게시글에 대한 상세 정보를 조회한다")
	@Test
	void success_find() {
		// given
		when(postRepository.find(any(PostDetailDbQuery.class)))
			.thenReturn(PostFixture.FIRST_POST);

		// when
		Post actual = postFinder.find(PostFixture.MEMBER_NO, PostFixture.FIRST_POST_NO);

		// then
		assertThat(actual).isNotNull().isEqualTo(PostFixture.FIRST_POST);
	}

	@DisplayName("조회하려는 게시글이 없으면 예외를 발생시킨다")
	@Test
	void fail_find() {
		// given
		when(postRepository.find(any(PostDetailDbQuery.class)))
			.thenReturn(null);

		// when, then
		assertThatThrownBy(() -> postFinder.find(PostFixture.MEMBER_NO, PostFixture.FIRST_POST_NO))
			.isInstanceOf(PostNotFoundException.class)
			.hasMessage(PostNotFoundException.notFound().getMessage());
	}

	@DisplayName("게시글과 연관된 미션을 성공한 다른 사람의 게시글을 조회한다")
	@Test
	void findAllRelated() {
	    // given
		when(postRepository.findRelated(any(RelatedPostDbQuery.class)))
			.thenReturn(PostFixture.RELATED_POST_FIRST_PAGE_RESULT);

	    // when
		RelatedPostResult actual = postFinder
			.findAllRelated(RelatedPostRetrieveQuery.of(PostFixture.MEMBER_NO, PostFixture.FIRST_MISSION_NO, 1, 5, null));

		// then
		assertThat(actual).isNotNull().isEqualTo(PostFixture.RELATED_POST_FIRST_PAGE_RESULT);
	}
}
