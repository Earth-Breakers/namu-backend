package univ.earthbreaker.namu.core.domain.reaction;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.common.ConflictException;

public class ReactionConflictException extends ConflictException {

	private ReactionConflictException(String message) {
		super(message);
	}

	public static @NotNull ReactionConflictException conflict(long targetNo) {
		return new ReactionConflictException(String.format("이미 존재하는 리액션 입니다 : target 번호 %s", targetNo));
	}
}
