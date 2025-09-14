package univ.earthbreaker.namu.core.service.reaction;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import univ.earthbreaker.namu.core.domain.reaction.ReactionConflictException;
import univ.earthbreaker.namu.core.domain.reaction.infra.ReactionDbQuery;
import univ.earthbreaker.namu.core.domain.reaction.infra.ReactionRepository;

@ExtendWith(MockitoExtension.class)
class ReactionManagerTest {

	private @Mock ReactionRepository reactionRepository;
	private @InjectMocks ReactionManager reactionManager;

	@DisplayName("회원이 리액션을 남기고자 하는 대상에 리액션을 하지 않았다면, 리액션을 등록한다")
	@Test
	void success_doReaction() {
		// given
		when(reactionRepository.alreadyReaction(any(ReactionDbQuery.class)))
			.thenReturn(false);

		// when
		reactionManager.doReaction(ReactionFixture.REACTION_COMMAND);

		// then
		verify(reactionRepository).reaction(ReactionFixture.REACTION_COMMAND.toDbCommand());
	}

	@DisplayName("회원이 리액션을 남기고자 하는 대상에 이미 리액션을 했다면, 예외를 발생시킨다")
	@Test
	void fail_doReaction() {
		// given
		when(reactionRepository.alreadyReaction(any(ReactionDbQuery.class)))
			.thenReturn(true);

		// when, then
		assertThatThrownBy(() -> reactionManager.doReaction(ReactionFixture.REACTION_COMMAND))
			.isInstanceOf(ReactionConflictException.class)
			.hasMessage(ReactionConflictException.conflict(ReactionFixture.TARGET_NO).getMessage());
	}

	@DisplayName("회원이 리액션을 취소하고자 하는 대상에 리액션이 등록되어 있었다면, 리액션을 취소한다")
	@Test
	void success_undoReaction() {
		// given
		when(reactionRepository.alreadyReaction(any(ReactionDbQuery.class)))
			.thenReturn(true);

		// when
		reactionManager.undoReaction(ReactionFixture.REACTION_COMMAND);

		// then
		verify(reactionRepository).cancelReaction(ReactionFixture.REACTION_COMMAND.toDbCommand());
	}

	@DisplayName("회원이 리액션을 취소하고자 하는 대상에 리액션이 등록되어 있지 않았다면, 아무런 동작도 하지 않는다")
	@Test
	void never_undoReaction() {
		// given
		when(reactionRepository.alreadyReaction(any(ReactionDbQuery.class)))
			.thenReturn(false);

		// when
		reactionManager.undoReaction(ReactionFixture.REACTION_COMMAND);

		// then
		verify(reactionRepository, never()).cancelReaction(ReactionFixture.REACTION_COMMAND.toDbCommand());
	}
}
