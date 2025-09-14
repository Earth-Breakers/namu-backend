package univ.earthbreaker.namu.core.service.post;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import univ.earthbreaker.namu.core.domain.post.infra.PostCreateDbCommand;
import univ.earthbreaker.namu.core.domain.post.infra.PostRepository;
import univ.earthbreaker.namu.event.post.PostCreateEvent;

@ExtendWith(MockitoExtension.class)
class PostCreateEventHandlerTest {

	private @Mock PostMemberBridge postMemberBridge;
	private @Mock PostRepository postRepository;
	private @InjectMocks PostCreateEventHandler postCreateEventHandler;

	@DisplayName("회원이 성공한 미션의 이미지와 글 내용, 미션의 제목을 통해 게시글 생성 이벤트를 구독해 생성한다")
	@Test
	void giveRewardPoint() {
		// given
		PostMemberBridge.PostMemberDto postMemberInfo = new PostMemberBridge.PostMemberDto(PostFixture.MEMBER_NO, PostFixture.MEMBER_NICKNAME);
		when(postMemberBridge.findMemberInfo(PostFixture.MEMBER_NO))
			.thenReturn(postMemberInfo);
		PostCreateEvent event = new PostCreateEvent(1L, "title", "content", "imagePathKey", 1L);

		// when
		postCreateEventHandler.createPost(event);

		// then
		verify(postRepository)
			.create(new PostCreateDbCommand(
				postMemberInfo.memberNo(),
				postMemberInfo.nickname(),
				event.title(),
				event.content(),
				event.imagePathKey(),
				event.missionNo()
			));
	}
}
