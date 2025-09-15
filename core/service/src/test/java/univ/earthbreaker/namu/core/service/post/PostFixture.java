package univ.earthbreaker.namu.core.service.post;

import java.time.LocalDate;
import java.util.List;

import univ.earthbreaker.namu.core.domain.post.Post;
import univ.earthbreaker.namu.core.domain.post.PostReactionResult;
import univ.earthbreaker.namu.core.domain.post.PostReactionResult.PostReactionStatus;
import univ.earthbreaker.namu.core.domain.post.PostReactionResult.PostReactionStatus.Info;
import univ.earthbreaker.namu.core.domain.post.RelatedPostResult;

public class PostFixture {

	public static final long MEMBER_NO = 1L;
	public static final String MEMBER_NICKNAME = "nickname";
	public static final long FIRST_POST_NO = 1L;
	public static final long SECOND_POST_NO = 2L;
	public static final long FIRST_MISSION_NO = 2L;
	public static final long SECOND_MISSION_NO = 3L;
	public static final String POST_IMAGE_PATH = "/post-dir/test.png";
	public static final LocalDate SEARCH_DATE = LocalDate.parse("2024-04-17");

	public static final Post FIRST_POST = Post.of(FIRST_POST_NO, MEMBER_NO, MEMBER_NICKNAME, "first-title", "content", POST_IMAGE_PATH, FIRST_MISSION_NO);
	public static final Post SECOND_POST = Post.of(SECOND_POST_NO, MEMBER_NO, MEMBER_NICKNAME, "second-title", "content", POST_IMAGE_PATH, SECOND_MISSION_NO);

	public static final List<Post> POSTS = List.of(FIRST_POST, SECOND_POST);

	public static final RelatedPostResult RELATED_POST_FIRST_PAGE_RESULT = new RelatedPostResult(POSTS, false);
	public static final RelatedPostResult RELATED_POST_LAST_PAGE_RESULT = new RelatedPostResult(POSTS, true);

	public static final Info POST_REACTION_INFO = new Info("LIKE", 1, List.of(2L));
	public static final PostReactionStatus POST_REACTION_STATUS = new PostReactionStatus(List.of(POST_REACTION_INFO), false);
	public static final PostReactionResult POST_REACTION_RESULT = new PostReactionResult(FIRST_POST,POST_REACTION_STATUS);
}
