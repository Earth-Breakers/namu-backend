package univ.earthbreaker.namu.core.domain.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static univ.earthbreaker.namu.core.domain.post.PostFixture.FIRST_MISSION_NO;
import static univ.earthbreaker.namu.core.domain.post.PostFixture.FIRST_POST;
import static univ.earthbreaker.namu.core.domain.post.PostFixture.FIRST_POST_NO;
import static univ.earthbreaker.namu.core.domain.post.PostFixture.MEMBER_NO;
import static univ.earthbreaker.namu.core.domain.post.PostFixture.RELATED_POST_FIRST_PAGE_RESULT;
import static univ.earthbreaker.namu.core.domain.post.PostFixture.SEARCH_DATE;
import static univ.earthbreaker.namu.core.domain.post.PostFixture.SECOND_POST;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostFinderTest {

	private @Mock PostRepository postRepository;
	private @InjectMocks PostFinder postFinder;

	@DisplayName("회원이 선택한 날짜에 성공한 미션에 대한 게시글들을 모두 조회한다")
	@Test
	void findAll() {
	    // given
		when(postRepository.findAll(any(PostDbQuery.class)))
			.thenReturn(List.of(FIRST_POST, SECOND_POST));

	    // when
		List<Post> actual = postFinder.findAll(MEMBER_NO, SEARCH_DATE);

		// then
		assertThat(actual).isNotNull().hasSize(2);
	}

	@DisplayName("회원의 게시글에 대한 상세 정보를 조회한다")
	@Test
	void success_find() {
		// given
		when(postRepository.find(any(PostDetailDbQuery.class)))
			.thenReturn(FIRST_POST);

		// when
		Post actual = postFinder.find(MEMBER_NO, FIRST_POST_NO);

		// then
		assertThat(actual).isNotNull().isEqualTo(FIRST_POST);
	}

	@DisplayName("조회하려는 게시글이 없으면 예외를 발생시킨다")
	@Test
	void fail_find() {
		// given
		when(postRepository.find(any(PostDetailDbQuery.class)))
			.thenReturn(null);

		// when, then
		assertThatThrownBy(() -> postFinder.find(MEMBER_NO, FIRST_POST_NO))
			.isInstanceOf(PostNotFoundException.class)
			.hasMessage(PostNotFoundException.notFound().getMessage());
	}

	@DisplayName("게시글과 연관된 미션을 성공한 다른 사람의 게시글을 조회한다")
	@Test
	void findAllRelated() {
	    // given
		when(postRepository.findRelated(any(RelatedPostDbQuery.class)))
			.thenReturn(RELATED_POST_FIRST_PAGE_RESULT);

	    // when
		RelatedPostResult actual = postFinder
			.findAllRelated(RelatedPostRetrieveQuery.of(MEMBER_NO, FIRST_MISSION_NO, 1, 5, null));

		// then
		assertThat(actual).isNotNull().isEqualTo(RELATED_POST_FIRST_PAGE_RESULT);
	}
}
