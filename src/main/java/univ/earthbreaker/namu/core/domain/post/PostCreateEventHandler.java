package univ.earthbreaker.namu.core.domain.post;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import univ.earthbreaker.namu.event.post.PostCreateEvent;

@Component
public class PostCreateEventHandler {

	private final PostRepository postRepository;

	public PostCreateEventHandler(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void createPost(@NotNull PostCreateEvent event) {
		PostCreateDbCommand createCommand = new PostCreateDbCommand(
			event.memberNo(),
			event.title(),
			event.content(),
			event.imagePathKey(),
			event.missionNo()
		);
		postRepository.create(createCommand);
	}
}
