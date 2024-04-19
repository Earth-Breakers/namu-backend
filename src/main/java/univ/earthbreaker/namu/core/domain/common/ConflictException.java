package univ.earthbreaker.namu.core.domain.common;

public abstract class ConflictException extends RuntimeException {

	protected ConflictException(String message) {
		super(message);
	}
}
