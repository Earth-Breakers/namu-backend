package univ.earthbreaker.namu.core.domain.post;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import univ.earthbreaker.namu.event.post.PostCreateEvent;

@Component
public class PostCreateEventHandler {

	private final PostMemberBridge postMemberBridge;
	private final PostRepository postRepository;

	public PostCreateEventHandler(PostMemberBridge postMemberBridge, PostRepository postRepository) {
		this.postMemberBridge = postMemberBridge;
		this.postRepository = postRepository;
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void createPost(@NotNull PostCreateEvent event) {
		PostMemberBridge.PostMemberDto postMemberInfo = postMemberBridge.findMemberInfo(event.memberNo());
		PostCreateDbCommand createCommand = new PostCreateDbCommand(
			postMemberInfo.memberNo(),
			postMemberInfo.nickname(),
			event.title(),
			event.content(),
			event.imagePathKey(),
			event.missionNo()
		);
		postRepository.create(createCommand);
	}
}
