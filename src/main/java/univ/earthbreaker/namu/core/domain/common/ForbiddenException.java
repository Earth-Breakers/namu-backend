package univ.earthbreaker.namu.core.domain.common;

public abstract class ForbiddenException extends RuntimeException {

	protected ForbiddenException(String message) {
		super(message);
	}
}
