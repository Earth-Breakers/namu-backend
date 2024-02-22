package univ.earthbreaker.namu.core.domain.common;

public abstract class InternalServerException extends RuntimeException {

	protected InternalServerException(String message) {
		super(message);
	}
}
